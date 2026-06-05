package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Get;

import java.util.List;

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
public class ObtenerCuponesPorVendedorController {

    private final CuponService cuponService;

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Cupon>> obtenerCuponesPorVendedor(@PathVariable Long vendedorId) {
        List<Cupon> cupones = cuponService.obtenerCuponesPorVendedor(vendedorId);
        return ResponseEntity.ok(cupones);
    }
}