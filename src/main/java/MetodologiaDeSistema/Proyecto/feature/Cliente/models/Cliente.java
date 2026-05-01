package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    
    // Debemos crear los atributos que tendra el cliente

    // utilizamos anotadores para hacer las validadciones
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank( message = "El nombre es requerido")
    private String nombre;

    @NotBlank( message = "El apellido es requerido")
    private String apellido;

    @NotNull(message="La direccion es requerida")
    @Embedded
    private Direccion direccion;

    @NotBlank(message="El mail es requerido")
    @Email
    private String email;

    @NotBlank(message="La contraseña es requerida")
    @Size(min=8, message="La contraseña tiene que tener al menos 8 caracteres")
    private String password;


}

