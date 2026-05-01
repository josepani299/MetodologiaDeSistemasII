package MetodologiaDeSistema.Proyecto.feature.carrito.models;

import jakarta.persistence.*;

@Entity
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double precio;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Producto producto;
}