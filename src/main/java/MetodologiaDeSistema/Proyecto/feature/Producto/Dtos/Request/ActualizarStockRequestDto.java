package MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarStockRequestDto {
    private Integer stockActual;
}
