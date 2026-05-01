package MetodologiaDeSistema.Proyecto.feature.carrito.controllers.post;

import MetodologiaDeSistema.Proyecto.feature.carrito.dtos.request.AgregarProductoRequestDto;
import MetodologiaDeSistema.Proyecto.feature.carrito.services.interfaces.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class AgregarProductoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{id}/items")
    public void agregar(@PathVariable Long id,
                        @RequestBody AgregarProductoRequestDto dto) {

        carritoService.agregarProducto(id, dto.productoId, dto.cantidad);
    }
}