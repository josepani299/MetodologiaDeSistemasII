package MetodologiaDeSistema.Proyecto.feature.Carrito.Dtos.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AgregarProductoRequestDto {

    private Long productoId;

    private Integer cantidad;
}