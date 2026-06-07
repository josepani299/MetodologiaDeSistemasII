package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/api/kits")
public class EliminarKitController {

    @Autowired
    private KitService kitService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarKit(@PathVariable Long id) {
        kitService.eliminarKit(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}