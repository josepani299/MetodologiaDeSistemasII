package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    
    @NotBlank(message="El pais es requerido")
    private String pais;

    @NotBlank(message="La provincia es requerido")
    private String provincia;

    @NotBlank(message="La localidad es requerido")
    private String localidad;

    @NotBlank(message="La calle es requerido")
    private String calle;

    @NotNull(message="El numero de calle es requerido")
    private int numeroCalle;

    @NotBlank(message="El pais es requerido")
    private String depto;


}
