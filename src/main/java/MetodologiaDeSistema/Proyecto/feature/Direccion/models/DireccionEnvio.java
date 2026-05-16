package MetodologiaDeSistema.Proyecto.feature.Direccion.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direcciones_envio", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"cliente_id", "pais", "provincia", "localidad", "calle", "numero_calle", "piso_depto"},
        name = "uk_direccion_unica_cliente"
    )
})
public class DireccionEnvio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore 
    private Cliente cliente;

    @NotBlank(message = "El país es requerido")
    private String pais;

    @NotBlank(message = "La provincia es requerida")
    private String provincia;

    @NotBlank(message = "La localidad es requerida")
    private String localidad;

    @NotBlank(message = "La calle es requerida")
    private String calle;

    @NotNull(message = "El número de calle es requerido")
    @Column(name = "numero_calle")
    private Integer numeroCalle;

    @NotBlank(message = "El piso/departamento es requerido")
    @Column(name = "piso_depto")
    private String pisoDepto;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private boolean activa = true;  // Para soft delete
}
