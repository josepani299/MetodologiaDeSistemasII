package MetodologiaDeSistema.Proyecto.feature.Carrito.Models;

import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double precio;

    @JsonBackReference
    @ManyToOne
    private Carrito carrito;

    // Producto: null cuando el item es un kit
    @ManyToOne
    private Producto producto;

    // Kit: null cuando el item es un producto individual
    // JPA agrega kit_id y kit_nombre a la tabla automáticamente (ddl-auto=update)
    private Long kitId;
    private String kitNombre;
}