package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.post;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Dtos.Request.AgregarKitRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class AgregarKitController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{carritoId}/kits")
    public ResponseEntity<Void> agregarKit(
            @PathVariable Long carritoId,
            @RequestBody AgregarKitRequestDto dto) {

        carritoService.agregarKit(carritoId, dto.getKitId(), dto.getCantidad());
        return ResponseEntity.ok().build();
    }
}