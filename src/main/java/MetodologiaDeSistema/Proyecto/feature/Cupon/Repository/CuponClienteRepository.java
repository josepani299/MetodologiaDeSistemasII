package MetodologiaDeSistema.Proyecto.feature.Cupon.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponCliente;

@Repository
public interface CuponClienteRepository extends JpaRepository<CuponCliente, Long> {

    List<CuponCliente> findByCuponId(Long cuponId);

    boolean existsByCuponIdAndClienteId(Long cuponId, Long clienteId);

    List<CuponCliente> findByCuponIdAndEmailEnviadoFalse(Long cuponId);

    void deleteByCuponId(Long cuponId);

    List<CuponCliente> findByEmailEnviadoFalse();

    Optional<CuponCliente> findByCuponAndClienteId(Cupon cupon, Long clienteId);
}