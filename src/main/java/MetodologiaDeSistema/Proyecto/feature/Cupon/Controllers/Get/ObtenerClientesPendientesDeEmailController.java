package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Get;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponClienteResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.CuponClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ObtenerClientesPendientesDeEmailController {

    private final CuponClienteService cuponClienteService;

    @GetMapping("/{cuponId}/clientes/pendientes-email")
    public ResponseEntity<List<CuponClienteResponseDto>> obtenerClientesPendientesDeEmail(@PathVariable Long cuponId) {
        List<CuponClienteResponseDto> clientes = cuponClienteService.obtenerClientesPendientesDeEmail(cuponId);
        return ResponseEntity.ok(clientes);
    }
}