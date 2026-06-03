package MetodologiaDeSistema.Proyecto.feature.kits.dtos.response;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KitResponseDto {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean estado;
    private Integer stockDisponible;
    private List<KitProductoResponseDto> productos;  // ← Con productos
}