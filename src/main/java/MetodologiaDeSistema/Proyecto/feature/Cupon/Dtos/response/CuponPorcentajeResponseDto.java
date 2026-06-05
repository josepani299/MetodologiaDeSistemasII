package MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponPorcentajeResponseDto {

    private Long id;
    private String codigo;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private BigDecimal porcentaje;
    private Long vendedorId;
    private LocalDateTime fechaCreacion;
}
