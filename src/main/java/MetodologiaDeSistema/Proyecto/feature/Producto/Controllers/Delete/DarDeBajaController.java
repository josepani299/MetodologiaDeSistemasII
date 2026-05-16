package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class DarDeBajaController {

    @Autowired
    private ProductoService productoService;

    @DeleteMapping("/{id}/baja")
    public ResponseEntity<ProductoResponseDto> darDeBaja(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.darDeBaja(id));
    }
}