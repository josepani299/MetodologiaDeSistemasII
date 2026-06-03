package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.get;


import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.StockCriticoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class StockCriticoController {

    private final ProductoService productoService;

    public StockCriticoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * GET /api/productos/stock-critico
     *
     * Devuelve los productos cuyo stockActual <= stockMinimo
     * con las unidades vendidas en los últimos 3 meses.
     * Simultáneamente dispara (async) el webhook de n8n para el
     * análisis IA y envío de email de reposición.
     */
    @GetMapping("/stock-critico")
    public ResponseEntity<List<StockCriticoResponseDto>> getStockCritico() {
        return ResponseEntity.ok(productoService.getStockCritico());
    }
}