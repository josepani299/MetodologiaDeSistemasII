package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.Post;

import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoPostImagenController {

    private final ProductoService productoService;

    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> importarImagen(
            @PathVariable Long id,
            @RequestParam("imagen") MultipartFile imagen) {
        return ResponseEntity.ok(productoService.importarImagen(id, imagen));
    }
}