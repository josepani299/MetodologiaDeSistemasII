package MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {
    
    private String codigo;           // "VALIDACION_ERROR", "DIRECCION_DUPLICADA"
    private String mensaje;          // Descripción del error
    private LocalDateTime timestamp; 
    private String path;            // /api/clientes/1/direcciones-envio
    private int status;             // 400, 409, 500
}
