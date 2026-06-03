package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.put;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class ModificarCantidadController {

    @Autowired
    private CarritoService carritoService;

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> modificarCantidad(@PathVariable Long itemId,
                                                  @RequestBody Map<String, Integer> body) {
        carritoService.modificarCantidad(itemId, body.get("cantidad"));
        return ResponseEntity.ok().build();
    }
}