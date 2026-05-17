package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import MetodologiaDeSistema.Proyecto.feature.kits.dtos.request.ActualizarKitRequestDto;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class ActualizarKitController {

    @Autowired
    private KitService kitService;

    @PutMapping("/{id}")
    public ResponseEntity<Kit> actualizarKit(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarKitRequestDto dto) {
        Kit kit = kitService.actualizarKit(id, dto.getNombre(), dto.getDescripcion(), dto.getPrecio());
        return ResponseEntity.ok(kit);
    }
}