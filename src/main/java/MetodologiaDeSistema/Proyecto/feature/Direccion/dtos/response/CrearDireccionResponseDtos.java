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

public class CrearDireccionResponseDtos {
    private Long id;
    private String pais;
    private String provincia;
    private String localidad;
    private String calle;
    private Integer numeroCalle;
    private String pisoDepto;
    private LocalDateTime fechaCreacion;
    private String mensaje;  
}
