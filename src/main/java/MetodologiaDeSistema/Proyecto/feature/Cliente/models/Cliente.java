package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;

@Data
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    
    // Debemos crear los atributos que tendra el cliente

    // utilizamos anotadores para hacer las validadciones

    // Faltaria agregar el role del cliente, en este caso deberiamos crear
    // una nueva clase role.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank( message = "El nombre es requerido")
    private String nombre;

    @NotBlank( message = "El apellido es requerido")
    private String apellido;

    @Embedded
    private Direccion direccion;

    @NotBlank(message="El mail es requerido")
    @Email
    private String email;

    @NotBlank(message="La contraseña es requerida")
    @Size(min=8, message="La contraseña tiene que tener al menos 8 caracteres")
    private String password;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<DireccionEnvio> direccionesEnvio = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.CLIENTE;


}

