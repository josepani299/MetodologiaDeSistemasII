package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.delete;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Service.Impl.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class EliminarItemController {

    @Autowired
    private CarritoService carritoService;

    @DeleteMapping("/items/{itemId}")
    public void eliminarItem(@PathVariable Long itemId) {

        carritoService.eliminarItem(itemId);
    }
}