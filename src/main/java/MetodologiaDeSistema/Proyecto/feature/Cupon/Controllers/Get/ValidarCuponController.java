package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.CuponService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ValidarCuponController {

    private final CuponService cuponService;

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Boolean> validarCupon(@PathVariable String codigo) {
        boolean esValido = cuponService.validarCupon(codigo);
        return ResponseEntity.ok(esValido);
    }
}