package MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.request;

import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Direccion;
import jakarta.validation.Valid;
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

public class RegistroRequestDto {
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellido;

    @NotBlank(message="El mail es requerido")
    @Email(message= "El mail tiene que tener la estrutura ...@...")
    private String email;

    @NotBlank(message="La contraseña es requerida")
    @Size(min=8,message="La contraseña tiene que tener un minimo de de 8 caracteres")
    private String password;

    @NotNull(message="La direccion es requerida")
    @Valid
    private Direccion direccion;


    

    


}
