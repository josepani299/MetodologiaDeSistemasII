package MetodologiaDeSistema.Proyecto.feature.kits.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitProductoResponseDto{
    
    private Long productoId;
    private Integer cantidad;
}