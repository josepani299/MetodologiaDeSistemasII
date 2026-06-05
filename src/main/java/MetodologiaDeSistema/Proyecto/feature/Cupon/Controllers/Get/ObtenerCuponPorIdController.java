package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.CuponService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ObtenerCuponPorIdController {

    private final CuponService cuponService;

    @GetMapping("/{id}")
    public ResponseEntity<Cupon> obtenerCupon(@PathVariable Long id) {
        Cupon cupon = cuponService.obtenerCupon(id);
        return ResponseEntity.ok(cupon);
    }
}