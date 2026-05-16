package MetodologiaDeSistema.Proyecto.feature.kits.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearKitRequestDto {
    
    @NotBlank(message = "El nombre del kit es requerido")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El precio del kit es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;
}
