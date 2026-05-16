package MetodologiaDeSistema.Proyecto.feature.kits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import java.util.List;

@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    
    List<Kit> findByEstadoTrue();
    
    Kit findByNombre(String nombre);
    
    List<Kit> findByEstadoFalse();
}