package MetodologiaDeSistema.Proyecto.feature.Cupon.Models;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PORCENTAJE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CuponPorcentaje extends Cupon {

    @NotNull(message = "El porcentaje es requerido")
    @DecimalMin(value = "0.01", message = "Mínimo 0.01%")
    @DecimalMax(value = "99.99", message = "Máximo 99.99%")
    private BigDecimal porcentaje;
}
