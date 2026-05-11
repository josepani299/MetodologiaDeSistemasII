package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.MedioPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConfirmarPedidoDto {
    @NotBlank(message = "La direccion es obligatoria")
    private String direccionEnvio;

    @NotNull(message = "Debe seleccionar un medio de pago")
    private MedioPago medioPago;
}
