package MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponClienteResponseDto {

    private Long id;
    private Long cuponId;
    private String codigoCupon;
    private ClienteResponseDto cliente;
    private boolean emailEnviado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaAsignacion;
}
