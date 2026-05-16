package MetodologiaDeSistema.Proyecto.feature.kits.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarProductoKitRequestDto{
    
    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;
    
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad = 1;
}
