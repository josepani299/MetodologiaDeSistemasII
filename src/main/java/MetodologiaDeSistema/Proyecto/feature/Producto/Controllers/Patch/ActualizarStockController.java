package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.Patch;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request.ActualizarStockRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productos")
public class ActualizarStockController {

    private final ProductoService productoService;

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDto> actualizarStock(
            @PathVariable Long id,
            @RequestBody ActualizarStockRequestDto dto) {
        return ResponseEntity.ok(productoService.actualizarStock(id, dto.getStockActual()));
    }
}