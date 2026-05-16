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

    @ManyToOne
    private Producto producto;
}