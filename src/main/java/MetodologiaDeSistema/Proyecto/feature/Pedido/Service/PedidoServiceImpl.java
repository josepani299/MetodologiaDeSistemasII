package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoItemRepository;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;
import MetodologiaDeSistema.Proyecto.feature.Direccion.repository.DireccionEnvioRepository;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.PedidoItemResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.PedidoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ProductoMasVendidoDto;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.models.KitProducto;
import MetodologiaDeSistema.Proyecto.feature.kits.repository.KitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.CarritoItem;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoRepository;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.EstadoPedido;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.Pedido;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.PedidoItem;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Repository.PedidoRepository;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ConfirmarPedidoDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidoRepository PedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoEmailService PedidoEmailService;

    @Autowired
    private DireccionEnvioRepository direccionEnvioRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private KitRepository kitRepository;

    @Transactional
    @Override
    public Long confirmarPedido(Long carritoId, ConfirmarPedidoDto dto) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("No se puede confirmar un carrito vacío");
        }

        Pedido pedido = new Pedido();

        Cliente cliente = clienteRepository.findByCarritoId(carritoId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        pedido.setCliente(cliente);

        DireccionEnvio dir = direccionEnvioRepository.findById(dto.getDireccionEnvioId())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
        pedido.setDireccionEnvio(dir.getCalle() + " " + dir.getNumeroCalle() + ", " + dir.getLocalidad());

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setMedioPago(dto.getMedioPago());

        List<PedidoItem> pedidoItems = new ArrayList<>();
        double total = 0;

        for (CarritoItem item : carrito.getItems()) {

            // ── CASO A: ES UN PRODUCTO INDIVIDUAL ──
            if (item.getProducto() != null) {
                Producto producto = item.getProducto();

                if (producto.getStockActual() < item.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para " + producto.getNombre());
                }

                producto.setStockActual(producto.getStockActual() - item.getCantidad());
                productoRepository.save(producto);

                PedidoItem pedidoItem = new PedidoItem();
                pedidoItem.setPedido(pedido);
                pedidoItem.setProducto(producto);
                pedidoItem.setCantidad(item.getCantidad());

                double precioUnidad = (item.getPrecio() != null) ? item.getPrecio() : producto.getPrecio();
                double subtotal = precioUnidad * item.getCantidad();
                pedidoItem.setSubtotal(subtotal);

                total += subtotal;
                pedidoItems.add(pedidoItem);
            }

            // ── CASO B: ES UN KIT DE PRODUCTOS ──
            else if (item.getKitId() != null) {
                Kit kit = kitRepository.findById(item.getKitId())
                        .orElseThrow(() -> new RuntimeException("Kit no encontrado: " + item.getKitNombre()));

                // 1. Validar primero que haya stock de TODO lo que compone al kit
                for (KitProducto kitProd : kit.getProductos()) {
                    Producto prodDelKit = productoRepository.findById(kitProd.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto del kit no encontrado"));

                    int cantidadNecesaria = kitProd.getCantidad() * item.getCantidad();
                    if (prodDelKit.getStockActual() < cantidadNecesaria) {
                        throw new RuntimeException("Stock insuficiente en el kit para " + prodDelKit.getNombre());
                    }
                }

                // 2. Calcular el precio regular total para aplicar proporcionalmente el descuento del kit
                double totalPrecioRegularKit = 0.0;
                Map<Long, Producto> productosMap = new HashMap<>();
                for (KitProducto kitProd : kit.getProductos()) {
                    Producto prodDelKit = productoRepository.findById(kitProd.getProductoId()).get();
                    productosMap.put(kitProd.getProductoId(), prodDelKit);
                    totalPrecioRegularKit += prodDelKit.getPrecio() * kitProd.getCantidad();
                }

                double precioKitActual = (item.getPrecio() != null) ? item.getPrecio() : kit.getPrecio();
                double factorDescuento = (totalPrecioRegularKit > 0) ? (precioKitActual / totalPrecioRegularKit) : 1.0;

                // 3. Descontar stock y guardar cada componente como un PedidoItem individual
                for (KitProducto kitProd : kit.getProductos()) {
                    Producto prodDelKit = productosMap.get(kitProd.getProductoId());
                    int cantidadNecesaria = kitProd.getCantidad() * item.getCantidad();

                    // Restar stock físico del producto
                    prodDelKit.setStockActual(prodDelKit.getStockActual() - cantidadNecesaria);
                    productoRepository.save(prodDelKit);

                    // Insertar al pedido desglosado
                    PedidoItem pedidoItem = new PedidoItem();
                    pedidoItem.setPedido(pedido);
                    pedidoItem.setProducto(prodDelKit);
                    pedidoItem.setCantidad(cantidadNecesaria);

                    // El precio se ajusta según el descuento real que ofrece el Kit
                    double precioUnitarioProrrateado = prodDelKit.getPrecio() * factorDescuento;
                    pedidoItem.setSubtotal(precioUnitarioProrrateado * cantidadNecesaria);

                    pedidoItems.add(pedidoItem);
                }

                // Sumamos el valor real del kit multiplicado por la cantidad de kits comprados
                total += precioKitActual * item.getCantidad();
            }
        }

        pedido.setItems(pedidoItems);
        pedido.setTotal(total);
        PedidoRepository.save(pedido);

        PedidoEmailService.enviarConfirmacion("cliente@gmail.com", pedido.getId());

        List<CarritoItem> itemsAEliminar = new ArrayList<>(carrito.getItems());
        carrito.getItems().clear();
        carrito.setTotal(0.0);
        carritoItemRepository.deleteAll(itemsAEliminar);
        carritoRepository.save(carrito);

        return pedido.getId();
    }

    @Transactional
    @Override
    public void actualizarEstado(Long pedidoId, EstadoPedido nuevoEstado) {

        Pedido pedido = PedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        EstadoPedido estadoActual = pedido.getEstado();

        if (estadoActual == EstadoPedido.ENTREGADO && nuevoEstado == EstadoPedido.PENDIENTE) {
            throw new RuntimeException("No se puede volver un pedido entregado a pendiente");
        }

        if (nuevoEstado == EstadoPedido.CANCELADO) {
            for (PedidoItem item : pedido.getItems()) {
                Producto producto = item.getProducto();
                producto.setStockActual(producto.getStockActual() + item.getCantidad());
                productoRepository.save(producto);
            }
        }

        pedido.setEstado(nuevoEstado);
        PedidoRepository.save(pedido);

        PedidoEmailService.enviarCambioEstado("cliente@gmail.com", pedido.getId(), nuevoEstado.name());
    }
    @Override
    public List<PedidoResponseDto> obtenerTodos() {
        return PedidoRepository.findAll().stream().map(p -> {
            PedidoResponseDto dto = new PedidoResponseDto();
            dto.setId(p.getId());
            dto.setClienteNombre(p.getCliente() != null
                    ? p.getCliente().getNombre() + " " + p.getCliente().getApellido()
                    : "—");
            dto.setFecha(p.getFecha());
            dto.setDireccionEnvio(p.getDireccionEnvio());
            dto.setTotal(p.getTotal());
            dto.setEstado(p.getEstado().name());
            dto.setMedioPago(p.getMedioPago().name());
            dto.setItems(p.getItems().stream().map(i -> {
                PedidoItemResponseDto itemDto = new PedidoItemResponseDto();
                itemDto.setId(i.getId());
                itemDto.setProductoNombre(i.getProducto().getNombre());
                itemDto.setCantidad(i.getCantidad());
                itemDto.setSubtotal(i.getSubtotal());
                return itemDto;
            }).toList());
            return dto;
        }).toList();
    }

    @Override
public List<ProductoMasVendidoDto> obtenerProductosMasVendidos(
        int mes,
        int anio) {

    List<Pedido> pedidos = PedidoRepository.findAll();

    Map<String, Integer> ventas = new HashMap<>();

    for (Pedido pedido : pedidos) {

        if (pedido.getFecha().getMonthValue() == mes &&
            pedido.getFecha().getYear() == anio) {

            for (PedidoItem item : pedido.getItems()) {

                String nombreProducto =
                        item.getProducto().getNombre();

                Integer cantidad = item.getCantidad();

                ventas.put(
                        nombreProducto,
                        ventas.getOrDefault(nombreProducto, 0)
                                + cantidad
                );
            }
        }
    }

    if (ventas.isEmpty()) {
    return new ArrayList<>();
    }

    return ventas.entrySet()
            .stream()
            .sorted((a, b) ->
                    b.getValue().compareTo(a.getValue()))
            .map(e -> new ProductoMasVendidoDto(e.getKey(), e.getValue())
            )
            .toList();
}
}