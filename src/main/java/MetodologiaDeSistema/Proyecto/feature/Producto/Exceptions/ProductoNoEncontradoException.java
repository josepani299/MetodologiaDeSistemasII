package MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(Long id) {
        super("No se encontró el producto con ID " + id);
    }
}