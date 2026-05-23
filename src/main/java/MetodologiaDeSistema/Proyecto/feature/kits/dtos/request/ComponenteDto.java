package MetodologiaDeSistema.Proyecto.feature.kits.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponenteDto {
    private Long id;
    private String tipo;
}