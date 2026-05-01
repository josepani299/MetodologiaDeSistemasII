package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    
    // Debemos crear los atributos que tendra el cliente

    // utilizamos anotadores para hacer las validadciones
    @NotBlank( message = "El nombre es requerido")
    private String nombre;

    @NotBlank( message = "El apellido es requerido")
    private String apellido;

    @NotNull(message="La direccion es requerida")
    private Direccion direccionEnvio;

    @NotBlank(message="El mail es requerido")
    @Email
    private String email;

    @NotBlank(message="La contraseña es requerida")
    @Size(min=0, max=8)
    private String password;


}

