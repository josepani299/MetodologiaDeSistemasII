package MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroResponseDtos {
    
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String mensaje;
    private boolean exitoso;
    private LocalDateTime fechaRegistro;
    
}
