package MetodologiaDeSistema.Proyecto.feature.Carrito.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total = 0.0;

    @JsonManagedReference
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoItem> items = new ArrayList<>();
}