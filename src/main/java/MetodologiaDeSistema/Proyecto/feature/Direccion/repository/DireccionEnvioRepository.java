package MetodologiaDeSistema.Proyecto.feature.Direccion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;

@Repository
public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Long> {
    
    // Buscar dirección duplicada exacta
    Optional<DireccionEnvio> findByClienteIdAndPaisAndProvinciaAndLocalidadAndCalleAndNumeroCalleAndPisoDepto(
        Long clienteId,
        String pais,
        String provincia,
        String localidad,
        String calle,
        Integer numeroCalle,
        String pisoDepto
    );
    
    // Obtener todas las direcciones activas de un cliente
    @Query("SELECT d FROM DireccionEnvio d WHERE d.cliente.id = :clienteId AND d.activa = true")
    List<DireccionEnvio> findDireccionesActivasPorCliente(@Param("clienteId") Long clienteId);
    
    // Obtener dirección por cliente e id
    @Query("SELECT d FROM DireccionEnvio d WHERE d.cliente.id = :clienteId AND d.id = :direccionId AND d.activa = true")
    Optional<DireccionEnvio> findByClienteIdAndId(@Param("clienteId") Long clienteId, @Param("direccionId") Long direccionId);
    
    // Contar direcciones activas de un cliente
    long countByClienteIdAndActivaTrue(Long clienteId);
}
