package MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReporteStockMinimoDto {

    private Long id;
    private String nombre;
    private Integer stockActual;
    private Integer stockMinimo;
}