package MetodologiaDeSistema.Proyecto.feature.kits.controllers.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.kits.dtos.request.CrearKitRequestDto;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.services.KitService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/kits")
public class CrearKitController {

    @Autowired
    private KitService kitService;

    @PostMapping
    public ResponseEntity<Kit> crearKit(@Valid @RequestBody CrearKitRequestDto dto) {
        Kit kit = kitService.crearKit(dto.getNombre(), dto.getDescripcion(), dto.getPrecio());
        return ResponseEntity.status(HttpStatus.CREATED).body(kit);
    }
}