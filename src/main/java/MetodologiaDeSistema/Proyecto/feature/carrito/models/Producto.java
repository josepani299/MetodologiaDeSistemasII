package MetodologiaDeSistema.Proyecto.feature.carrito.models;

import jakarta.persistence.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    private Integer stock;

    // getters y setters
}