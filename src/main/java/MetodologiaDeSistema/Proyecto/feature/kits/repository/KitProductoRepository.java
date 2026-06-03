package MetodologiaDeSistema.Proyecto.feature.kits.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import MetodologiaDeSistema.Proyecto.feature.kits.models.KitProducto;

@Repository
public interface KitProductoRepository extends JpaRepository<KitProducto, Long> {
        
    List<KitProducto> findByKitId(Long kitId);
    
    @Query("SELECT kp FROM KitProducto kp WHERE kp.kit.id = :kitId AND kp.productoId = :productoId")
    Optional<KitProducto> findByKitIdAndProductoId(@Param("kitId") Long kitId, @Param("productoId") Long productoId);
    
    void deleteByKitId(Long kitId);
}