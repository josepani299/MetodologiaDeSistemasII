package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    @Override
    public void confirmarPedido(Long carritoId, ConfirmarPedidoDto dto) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new RuntimeException("Carrito no encontrado"));

        if(carrito.getItems().isEmpty()) {

            throw new RuntimeException(
                    "No se puede confirmar un carrito vacio"
            );
        }

        Pedido pedido = new Pedido();

        pedido.setDireccionEnvio(dto.getDireccionEnvio());

        pedido.setEstado(EstadoPedido.PENDIENTE);

        pedido.setMedioPago(dto.getMedioPago());

        List<PedidoItem> pedidoItems = new ArrayList<>();

        double total = 0;

        for(CarritoItem item : carrito.getItems()) {

            Producto producto = item.getProducto();

            if(producto.getStockActual()
                    < item.getCantidad()) {

                throw new RuntimeException(
                        "Stock insuficiente para " + producto.getNombre()
                );
            }

            producto.setStockActual(
                    producto.getStockActual() - item.getCantidad()
            );

            productoRepository.save(producto);

            PedidoItem pedidoItem = new PedidoItem();

            pedidoItem.setPedido(pedido);

            pedidoItem.setProducto(producto);

            pedidoItem.setCantidad(item.getCantidad());

            double subtotal =
                    producto.getPrecio()
                            * item.getCantidad();

            pedidoItem.setSubtotal(subtotal);

            total += subtotal;

            pedidoItems.add(pedidoItem);
        }

        pedido.setItems(pedidoItems);

        pedido.setTotal(total);

        PedidoRepository.save(pedido);

        PedidoEmailService.enviarConfirmacion(
                "cliente@gmail.com",
                pedido.getId()
        );
    }
}  