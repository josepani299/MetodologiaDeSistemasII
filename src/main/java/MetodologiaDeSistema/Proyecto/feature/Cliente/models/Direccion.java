package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {
    
    @NotBlank(message="El pais es requerido")
     @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "El Pais solo puede contener letras"
    )
    private String pais;


    @NotBlank(message="La provincia es requerido")
     @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "La provincia solo puede contener letras"
    )
    private String provincia;

    @NotBlank(message="La localidad es requerido")
     @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "La localidad solo puede contener letras"
    )
    private String localidad;

    @NotBlank(message="La calle es requerido")
     @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$",
        message = "La calle solo puede contener letras"
    )
    private String calle;

    @NotNull(message="El numero de calle es requerido")
    private int numeroCalle;

    
    private String depto;


}
