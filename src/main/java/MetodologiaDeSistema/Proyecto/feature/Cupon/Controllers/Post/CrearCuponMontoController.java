package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Post;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request.CrearCuponMontoRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponMontoResponseDto;  // ← SIN Dto
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
public class CrearCuponMontoController {

    private final CuponService cuponService;

    @PostMapping("/monto")
    public ResponseEntity<CuponMontoResponseDto> crearCuponMonto(  
            @Valid @RequestBody CrearCuponMontoRequestDto request,
            @RequestHeader("X-Usuario-Id") Long vendedorId) {
        
        CuponMontoResponseDto response = cuponService.crearCuponMonto(request, vendedorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}