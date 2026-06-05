package MetodologiaDeSistema.Proyecto.feature.Cupon.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigo;

    @NotNull (message="La fecha de alta del cupon es requerida")
    private LocalDate fechaAlta;

    @NotNull (message= "La fecha de finalizacion es requerida")
    private LocalDate fechaVencimiento;

    private TipoDescuento tipoDescuento;

    private boolean activo; 

    
    
}   
