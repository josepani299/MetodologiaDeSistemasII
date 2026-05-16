package MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Impl;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.CarritoItem;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoItemRepository;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoRepository;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface.CarritoService;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
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

        if (producto.getStockActual() < cantidad) {
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

        if (item.getProducto().getStockActual() < cantidad) {
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