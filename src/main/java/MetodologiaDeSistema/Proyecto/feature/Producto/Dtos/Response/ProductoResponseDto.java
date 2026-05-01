package MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto {

    private Long id;
    
    private String nombre;

    private String marca;

    private Double precio;

    private Integer stockActual;

    private Integer stockMinimo;
}
