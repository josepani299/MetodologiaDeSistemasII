package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.EstadoPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarEstadoPedidoDto {
    @NotNull(message = "El estado es obligatorio")
    private EstadoPedido nuevoEstado;
}
