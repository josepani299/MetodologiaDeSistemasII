package MetodologiaDeSistema.Proyecto.feature.carrito.repository;

import MetodologiaDeSistema.Proyecto.feature.carrito.models.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}