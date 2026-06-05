package MetodologiaDeSistema.Proyecto.feature.Cupon.Repository;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {

    boolean existsByCodigo(String codigo);

    Optional<Cupon> findByCodigo(String codigo);

    List<Cupon> findByVendedorId(Long vendedorId);

    List<Cupon> findByFechaInicioBeforeAndFechaVencimientoAfter(LocalDate fechaInicio, LocalDate fechaVencimiento);
}