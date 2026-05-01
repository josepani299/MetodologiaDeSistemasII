package MetodologiaDeSistema.Proyecto.feature.carrito.repository;

import MetodologiaDeSistema.Proyecto.feature.carrito.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}