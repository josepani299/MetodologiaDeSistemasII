package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;
import lombok.Data;

@Data
public class ProductoMasVendidoDto {
    private String productoNombre;
    private Integer cantidadVendida;

    public ProductoMasVendidoDto(
            String productoNombre,
            Integer cantidadVendida) {

        this.productoNombre = productoNombre;
        this.cantidadVendida = cantidadVendida;
    }
}
