package MetodologiaDeSistema.Proyecto.feature.carrito.services.interfaces;

public interface CarritoService {

    void agregarProducto(Long carritoId, Long productoId, Integer cantidad);

    void eliminarItem(Long itemId);

    void modificarCantidad(Long itemId, Integer cantidad);
}