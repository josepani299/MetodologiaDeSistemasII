package MetodologiaDeSistema.Proyecto.feature.Carrito.Repository;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}