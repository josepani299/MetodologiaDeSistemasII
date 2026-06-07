package MetodologiaDeSistema.Proyecto.feature.Carrito.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarKitRequestDto {

    private Long kitId;

    private Integer cantidad;
}