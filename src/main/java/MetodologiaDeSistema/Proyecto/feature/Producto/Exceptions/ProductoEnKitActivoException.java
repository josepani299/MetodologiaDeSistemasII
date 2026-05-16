package MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions;

public class ProductoEnKitActivoException extends RuntimeException {
    public ProductoEnKitActivoException(Long id) {
        super("No se puede dar de baja el producto con ID " + id +
              " porque pertenece a un kit activo.");
    }
}