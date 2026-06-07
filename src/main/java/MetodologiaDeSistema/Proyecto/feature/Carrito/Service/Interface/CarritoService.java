package MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface;

public interface CarritoService {

    void agregarProducto(Long carritoId, Long productoId, Integer cantidad);

    // Error 4 — nuevo método para agregar un kit como ítem único con su precio
    void agregarKit(Long carritoId, Long kitId, Integer cantidad);

    void eliminarItem(Long itemId);

    void modificarCantidad(Long itemId, Integer cantidad);
}