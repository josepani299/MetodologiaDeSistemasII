package MetodologiaDeSistema.Proyecto.feature.Cupon.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cupon_id", nullable = false)
    private Cupon cupon;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "El estado de envío es requerido")
    private boolean emailEnviado = false;

    private LocalDateTime fechaEnvio;

    @CreationTimestamp
    private LocalDateTime fechaAsignacion;
    private boolean usado = false;
    private LocalDateTime fechaUso;
}
