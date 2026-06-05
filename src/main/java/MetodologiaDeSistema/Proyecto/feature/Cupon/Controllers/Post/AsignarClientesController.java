package MetodologiaDeSistema.Proyecto.feature.Cupon.Controllers.Post;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request.AsignarClientesRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponClienteResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.CuponClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AsignarClientesController {

    private final CuponClienteService cuponClienteService;

    @PostMapping("/{cuponId}/asignar-clientes")
    public ResponseEntity<List<CuponClienteResponseDto>> asignarClientes(
            @PathVariable Long cuponId,
            @Valid @RequestBody AsignarClientesRequestDto request) {
        
        List<CuponClienteResponseDto> response = cuponClienteService.asignarClientes(cuponId, request.getClienteIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}