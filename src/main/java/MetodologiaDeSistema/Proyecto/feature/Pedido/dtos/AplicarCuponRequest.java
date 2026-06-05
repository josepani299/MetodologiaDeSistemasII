package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AplicarCuponRequest {

    @NotNull(message = "El id del cliente es requerido")
    private Long clienteId;

    @NotBlank(message = "El código del cupón es requerido")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}", message = "Formato de código inválido")
    private String codigoCupon;
}