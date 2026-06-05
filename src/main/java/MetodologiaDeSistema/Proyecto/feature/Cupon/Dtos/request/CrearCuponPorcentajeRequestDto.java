
package MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearCuponPorcentajeRequestDto {

    @NotNull(message = "La fecha de inicio es requerida")
      @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de vencimiento es requerida")
      @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El porcentaje de descuento es requerido")
    @DecimalMin(value = "0.01", message = "El porcentaje mínimo es 0.01%")
    @DecimalMax(value = "99.99", message = "El porcentaje máximo es 99.99%")
    private BigDecimal porcentaje;
}