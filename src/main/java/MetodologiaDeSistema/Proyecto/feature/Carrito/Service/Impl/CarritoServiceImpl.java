package MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Impl;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.CarritoItem;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoItemRepository;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoRepository;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface.CarritoService;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.repository.KitRepository;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository itemRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private KitRepository kitRepository;

    @Autowired
    private KitService kitService;

    @Override
    public void agregarProducto(Long carritoId, Long productoId, Integer cantidad) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStockActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CarritoItem item = new CarritoItem();
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setPrecio(producto.getPrecio());   // precio individual del producto
        item.setCarrito(carrito);
        // kitId y kitNombre quedan null (es un producto suelto)

        carrito.getItems().add(item);
        carrito.setTotal(calcularTotal(carrito));
        carritoRepository.save(carrito);
    }

    // ERROR 4 — agregar un kit como ítem único con el precio del kit (no la suma de sus productos)
    @Override
    public void agregarKit(Long carritoId, Long kitId, Integer cantidad) {

        Kit kit = kitRepository.findById(kitId)
                .orElseThrow(() -> new RuntimeException("Kit no encontrado con ID: " + kitId));

        if (!kit.getEstado()) {
            throw new RuntimeException("El kit no está disponible");
        }

        // Verificar stock: stockDisponible = mínimo(stockProducto / cantidadRequerida) para cada producto del kit
        Integer stockDisponible = kitService.obtenerStockDisponibleKit(kitId);
        if (stockDisponible < cantidad) {
            throw new RuntimeException("Stock insuficiente para el kit");
        }

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CarritoItem item = new CarritoItem();
        item.setKitId(kitId);
        item.setKitNombre(kit.getNombre());
        item.setPrecio(kit.getPrecio());    // precio del kit, NO la suma de sus productos
        item.setCantidad(cantidad);
        item.setCarrito(carrito);
        // producto queda null (es un kit, no un producto suelto)

        carrito.getItems().add(item);
        carrito.setTotal(calcularTotal(carrito));
        carritoRepository.save(carrito);
    }

    @Override
    public void eliminarItem(Long itemId) {

        CarritoItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Carrito carrito = item.getCarrito();
        carrito.getItems().remove(item);
        itemRepository.delete(item);
        carrito.setTotal(calcularTotal(carrito));
        carritoRepository.save(carrito);
    }

    @Override
    public void modificarCantidad(Long itemId, Integer cantidad) {

        CarritoItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        // ERROR 4 fix — antes haría NPE en items de kit porque producto es null
        if (item.getKitId() != null) {
            // Es un kit: verificar stock del kit
            Integer stockDisponible = kitService.obtenerStockDisponibleKit(item.getKitId());
            if (stockDisponible < cantidad) {
                throw new RuntimeException("Stock insuficiente para el kit");
            }
        } else {
            // Es un producto suelto: verificar stock normal
            if (item.getProducto().getStockActual() < cantidad) {
                throw new RuntimeException("Stock insuficiente");
            }
        }

        item.setCantidad(cantidad);
        itemRepository.save(item);

        Carrito carrito = item.getCarrito();
        carrito.setTotal(calcularTotal(carrito));
        carritoRepository.save(carrito);
    }

    private Double calcularTotal(Carrito carrito) {
        return carrito.getItems()
                .stream()
                .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                .sum();
    }
}