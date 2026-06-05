package MetodologiaDeSistema.Proyecto.feature.Cupon.Models;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MONTO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CuponMonto extends Cupon {

    @NotNull(message = "El monto de descuento es requerido")
    @DecimalMin(value = "0.01", message = "Mínimo 0.01")
    private BigDecimal montoDescuento;

    @NotNull(message = "El monto mínimo de compra es requerido")
    @DecimalMin(value = "0.01", message = "Mínimo 0.01")
    private BigDecimal montoMinimoCompra;

    @PrePersist
    @PreUpdate
    public void validar() {
        if (montoDescuento.compareTo(montoMinimoCompra) >= 0) {
            throw new IllegalArgumentException(
                "El descuento no puede ser mayor o igual al monto mínimo"
            );
        }
    }
}