package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.get;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ReporteStockMinimoDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Interface.ReporteStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReporteStockController {

    private final ReporteStockService reporteStockService;

    @GetMapping("/stock-minimo")
    public List<ReporteStockMinimoDto> obtenerReporte(
            @RequestParam Integer porcentaje
    ) {

        return reporteStockService.obtenerReporte(porcentaje);
    }
}