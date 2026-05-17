package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Patch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class DarDeBajaKitController {

    @Autowired
    private KitService kitService;

    @PatchMapping("/{id}/dar-de-baja")
    public ResponseEntity<Kit> darDeBajaKit(@PathVariable Long id) {
        Kit kit = kitService.darDeBajaKit(id);
        return ResponseEntity.ok(kit);
    }
}