package MetodologiaDeSistema.Proyecto.feature.carrito.controllers.put;

import MetodologiaDeSistema.Proyecto.feature.carrito.services.interfaces.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class ModificarCantidadController {

    @Autowired
    private CarritoService carritoService;

    @PutMapping("/items/{itemId}")
    public void modificar(@PathVariable Long itemId,
                          @RequestParam Integer cantidad) {

        carritoService.modificarCantidad(itemId, cantidad);
    }
}