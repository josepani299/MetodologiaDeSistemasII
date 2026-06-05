package MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignarClientesRequestDto {

    @NotNull(message = "La lista de clientes es requerida")
    private List<Long> clienteIds;
}