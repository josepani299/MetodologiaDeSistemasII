package MetodologiaDeSistema.Proyecto.feature.carrito.repository;

import MetodologiaDeSistema.Proyecto.feature.carrito.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}