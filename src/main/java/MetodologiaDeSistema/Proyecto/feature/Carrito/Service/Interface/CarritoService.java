package MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface;

public interface CarritoService {

    void agregarProducto(Long carritoId, Long productoId, Integer cantidad);

    void eliminarItem(Long itemId);

    void modificarCantidad(Long itemId, Integer cantidad);
}