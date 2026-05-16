package MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions;

public class ProductoYaInactivoException extends RuntimeException {
    public ProductoYaInactivoException(Long id) {
        super("El producto con ID " + id + " ya está dado de baja.");
    }
}