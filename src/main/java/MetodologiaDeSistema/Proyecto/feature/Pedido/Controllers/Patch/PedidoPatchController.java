package MetodologiaDeSistema.Proyecto.feature.Pedido.Controllers.Patch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Service.PedidoService;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ActualizarEstadoPedidoDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
public class PedidoPatchController {
    @Autowired
    private PedidoService pedidoService;

    @PatchMapping("{pedidoId}/estado")
    public ResponseEntity<?> actualizarEstado(
        @PathVariable Long pedidoId,
        @RequestBody @Valid ActualizarEstadoPedidoDto dto){
            pedidoService.actualizarEstado(
                pedidoId,
                dto.getNuevoEstado()
            );
            return ResponseEntity.ok("Estado actualizado correctamente");
        }
}
