package MetodologiaDeSistema.Proyecto.feature.Pedido.Models;

import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double subtotal;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Pedido pedido;
}
