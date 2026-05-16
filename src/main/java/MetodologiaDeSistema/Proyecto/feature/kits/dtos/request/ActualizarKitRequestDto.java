package MetodologiaDeSistema.Proyecto.feature.kits.dtos.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarKitRequestDto {
    
    private String nombre;
    
    private String descripcion;
    
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;
}