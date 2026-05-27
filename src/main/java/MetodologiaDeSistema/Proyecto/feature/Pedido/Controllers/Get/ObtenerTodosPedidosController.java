package MetodologiaDeSistema.Proyecto.feature.Pedido.Controllers.Get;

import java.util.List;

import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.PedidoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ProductoMasVendidoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/reporte")
    public ResponseEntity<List<ProductoMasVendidoDto>> obtenerReporte(
            @RequestParam int mes,
            @RequestParam int anio) {

        return ResponseEntity.ok(
                pedidoService.obtenerProductosMasVendidos(
                        mes,
                        anio
                )
        );
    }
}