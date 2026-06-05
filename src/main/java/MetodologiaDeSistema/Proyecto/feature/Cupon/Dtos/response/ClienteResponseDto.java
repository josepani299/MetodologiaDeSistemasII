package MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}