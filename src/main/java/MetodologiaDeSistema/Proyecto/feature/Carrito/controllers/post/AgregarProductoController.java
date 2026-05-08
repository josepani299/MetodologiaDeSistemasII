package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.post;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Dtos.Request.AgregarProductoRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Impl.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class AgregarProductoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{id}/items")
    public void agregarProducto(@PathVariable Long id,
                                @RequestBody AgregarProductoRequestDto dto) {

        carritoService.agregarProducto(id, dto.getProductoId(), dto.getCantidad());
    }
}