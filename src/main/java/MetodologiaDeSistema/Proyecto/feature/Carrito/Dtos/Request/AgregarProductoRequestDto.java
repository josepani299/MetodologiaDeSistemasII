package MetodologiaDeSistema.Proyecto.feature.Carrito.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarProductoRequestDto {

    private Long productoId;

    private Integer cantidad;
}