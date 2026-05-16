package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class ObtenerKitPorIdController {

    @Autowired
    private KitService kitService;

    @GetMapping("/{id}")
    public ResponseEntity<Kit> obtenerKitPorId(@PathVariable Long id) {
        Kit kit = kitService.obtenerKitPorId(id);
        return ResponseEntity.ok(kit);
    }
}