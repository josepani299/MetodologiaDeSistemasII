package MetodologiaDeSistema.Proyecto.feature.Cupon.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cupon")
public abstract class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}")
    @Column(unique = true)
    private String codigo;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de vencimiento es requerida")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El vendedor es requerido")
    private Long vendedorId;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void generarCodigo() {
        if (this.codigo == null) {
            this.codigo = generarCodigoCupon();
        }
    }

    private static String generarCodigoCupon() {
        Random random = new Random();
        return String.format("%03d-%03d-%03d",
            random.nextInt(1000),
            random.nextInt(1000),
            random.nextInt(1000));
    }
}