package MetodologiaDeSistema.Proyecto.feature.Pedido.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.Pedido;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido, Long>{
    
}
