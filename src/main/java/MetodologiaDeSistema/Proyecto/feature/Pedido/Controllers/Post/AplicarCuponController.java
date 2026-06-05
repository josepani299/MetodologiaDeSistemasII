package MetodologiaDeSistema.Proyecto.feature.Pedido.Controllers.Post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Service.AplicarCuponService;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.AplicarCuponRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class AplicarCuponController {

    @Autowired
    private AplicarCuponService aplicarCuponService;

    @PostMapping("/{pedidoId}/aplicar-cupon")
    public ResponseEntity<?> aplicarCupon(
            @PathVariable Long pedidoId,
            @RequestBody @Valid AplicarCuponRequest request) {
        try {
            Double nuevoTotal = aplicarCuponService.aplicarCupon(
                    pedidoId,
                    request.getClienteId(),
                    request.getCodigoCupon()
            );
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Cupón aplicado correctamente",
                    "nuevoTotal", nuevoTotal
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}