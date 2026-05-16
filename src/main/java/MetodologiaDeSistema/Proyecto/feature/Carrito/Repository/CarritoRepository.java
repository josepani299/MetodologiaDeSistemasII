package MetodologiaDeSistema.Proyecto.feature.Carrito.Repository;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}