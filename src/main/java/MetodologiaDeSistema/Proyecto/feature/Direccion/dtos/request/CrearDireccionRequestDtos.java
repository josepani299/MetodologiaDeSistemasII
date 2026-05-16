package MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearDireccionRequestDtos {
    
    @NotBlank(message = "El país es requerido")
    private String pais;

    @NotBlank(message = "La provincia/estado es requerida")
    private String provincia;

    @NotBlank(message = "La localidad es requerida")
    private String localidad;

    @NotBlank(message = "La calle es requerida")
    private String calle;

    @NotNull(message = "El número de calle es requerido")
    @Min(value = 1, message = "El número de calle debe ser mayor a 0")
    private Integer numeroCalle;

    @NotBlank(message = "El piso/departamento es requerido")
    private String pisoDepto;
}
