package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;

@RestController
@RequestMapping("/kits")
public class EliminarProductoDelKitController {

    @Autowired
    private KitService kitService;

    @DeleteMapping("/{kitId}/productos/{kitProductoId}")
    public ResponseEntity<Void> eliminarProductoDelKit(
            @PathVariable Long kitId,
            @PathVariable Long kitProductoId) {
        kitService.eliminarProductoDelKit(kitId, kitProductoId);
        return ResponseEntity.noContent().build();
    }
}