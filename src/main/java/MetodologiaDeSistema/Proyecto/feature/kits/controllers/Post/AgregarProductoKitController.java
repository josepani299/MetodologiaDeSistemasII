package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import MetodologiaDeSistema.Proyecto.feature.kits.dtos.request.AgregarProductoKitRequestDto;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class AgregarProductoKitController {

    @Autowired
    private KitService kitService;

    @PostMapping("/{kitId}/productos")
    public ResponseEntity<Kit> agregarProductoAKit(
            @PathVariable Long kitId,
            @Valid @RequestBody AgregarProductoKitRequestDto dto) {
        kitService.agregarProductoAKit(kitId, dto.getProductoId(), dto.getCantidad());
        Kit kitActualizado = kitService.obtenerKitPorId(kitId);
        return ResponseEntity.status(HttpStatus.CREATED).body(kitActualizado);
    }
}