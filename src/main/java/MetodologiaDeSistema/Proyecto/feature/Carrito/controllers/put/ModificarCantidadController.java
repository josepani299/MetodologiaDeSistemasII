package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.put;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Interface.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class ModificarCantidadController {

    @Autowired
    private CarritoService carritoService;

    @PutMapping("/items/{itemId}")
    public void modificarCantidad(@PathVariable Long itemId,
                                  @RequestParam Integer cantidad) {

        carritoService.modificarCantidad(itemId, cantidad);
    }
}