package MetodologiaDeSistema.Proyecto.feature.Pedido.Controllers.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Service.PedidoService;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ConfirmarPedidoDto;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> confirmarPedido(@RequestBody @Valid ConfirmarPedidoDto dto) {
        Long id = pedidoService.confirmarPedido(dto.getCarritoId(), dto);
        return ResponseEntity.ok(Map.of("id", id));
    }
}
