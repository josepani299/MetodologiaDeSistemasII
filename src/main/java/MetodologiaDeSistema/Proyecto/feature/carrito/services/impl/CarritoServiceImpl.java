package MetodologiaDeSistema.Proyecto.feature.carrito.services.impl;

import MetodologiaDeSistema.Proyecto.feature.carrito.models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.carrito.models.CarritoItem;
import MetodologiaDeSistema.Proyecto.feature.carrito.models.Producto;
import MetodologiaDeSistema.Proyecto.feature.carrito.repository.CarritoItemRepository;
import MetodologiaDeSistema.Proyecto.feature.carrito.repository.CarritoRepository;
import MetodologiaDeSistema.Proyecto.feature.carrito.repository.ProductoRepository;
import MetodologiaDeSistema.Proyecto.feature.carrito.services.interfaces.CarritoService;
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

    @Override
    public void agregarProducto(Long carritoId, Long productoId, Integer cantidad) {

        Producto producto = productoRepository.findById(productoId).orElseThrow();

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        CarritoItem item = new CarritoItem();
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setPrecio(producto.getPrecio());
        item.setCarrito(carrito);

        carrito.getItems().add(item);
        carrito.setTotal(calcularTotal(carrito));

        carritoRepository.save(carrito);
    }

    @Override
    public void eliminarItem(Long itemId) {

        CarritoItem item = itemRepository.findById(itemId).orElseThrow();
        Carrito carrito = item.getCarrito();

        carrito.getItems().remove(item);
        itemRepository.delete(item);

        carrito.setTotal(calcularTotal(carrito));
        carritoRepository.save(carrito);
    }

    @Override
    public void modificarCantidad(Long itemId, Integer cantidad) {

        CarritoItem item = itemRepository.findById(itemId).orElseThrow();

        if (item.getProducto().getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
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