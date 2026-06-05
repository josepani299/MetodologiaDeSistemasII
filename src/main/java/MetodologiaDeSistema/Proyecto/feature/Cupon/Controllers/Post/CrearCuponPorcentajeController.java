package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Post;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request.CrearCuponPorcentajeRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponPorcentajeResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.CuponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CrearCuponPorcentajeController {

    private final CuponService cuponService;

    @PostMapping("/porcentaje")
    public ResponseEntity<CuponPorcentajeResponseDto> crearCuponPorcentaje(
            @Valid @RequestBody CrearCuponPorcentajeRequestDto request,
            @RequestHeader("X-Usuario-Id") Long vendedorId) {
        
        CuponPorcentajeResponseDto response = cuponService.crearCuponPorcentaje(request, vendedorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}