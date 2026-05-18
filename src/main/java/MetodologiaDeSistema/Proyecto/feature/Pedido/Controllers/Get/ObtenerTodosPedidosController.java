package MetodologiaDeSistema.Proyecto.feature.Pedido.Controllers.Get;

import java.util.List;

import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.PedidoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class ObtenerTodosPedidosController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }
}