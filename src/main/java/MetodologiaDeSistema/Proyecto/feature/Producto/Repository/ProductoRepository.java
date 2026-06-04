package MetodologiaDeSistema.Proyecto.feature.Producto.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.StockCriticoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // ── Métodos existentes (no tocar) ────────────────────────────────────────
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    List<Producto> findByActivoTrue();
    List<Producto> findByStockActualLessThanEqual(Integer stock);
    default boolean existeEnKitActivo(Long productoId) {
        return false;
    }

    // ── US16 - Agregado ──────────────────────────────────────────────────────
    @Query("""
            SELECT new MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.StockCriticoResponseDto(
                p.id, p.nombre, p.marca,
                p.stockActual, p.stockMinimo,
                COALESCE(SUM(pi.cantidad), 0)
            )
            FROM Producto p
            LEFT JOIN PedidoItem pi ON pi.producto = p
            LEFT JOIN pi.pedido ped ON ped.fecha >= :desde
            WHERE p.activo = true
              AND p.stockActual <= p.stockMinimo
            GROUP BY p.id, p.nombre, p.marca, p.stockActual, p.stockMinimo
            ORDER BY p.stockActual ASC
            """)
    List<StockCriticoResponseDto> findStockCriticoConVentas(@Param("desde") LocalDateTime desde);
}