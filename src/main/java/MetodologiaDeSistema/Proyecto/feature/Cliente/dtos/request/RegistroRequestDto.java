package MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegistroRequestDto {
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    


}
