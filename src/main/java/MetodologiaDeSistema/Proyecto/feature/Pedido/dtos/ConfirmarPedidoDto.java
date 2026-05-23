package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.MedioPago;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmarPedidoDto {
    @NotNull
    private Long carritoId;

    @NotNull
    private Long direccionEnvioId;

    @NotNull(message = "Debe seleccionar un medio de pago")
    private MedioPago medioPago;
}