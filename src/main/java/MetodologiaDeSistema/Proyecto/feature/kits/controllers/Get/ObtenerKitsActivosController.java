package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Get;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class ObtenerKitsActivosController {

    @Autowired
    private KitService kitService;

    @GetMapping("/activos")
    public ResponseEntity<List<Kit>> obtenerKitsActivos() {
        List<Kit> kits = kitService.obtenerKitsActivos();
        return ResponseEntity.ok(kits);
    }
}