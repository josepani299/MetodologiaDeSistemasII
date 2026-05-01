package MetodologiaDeSistema.Proyecto.feature.carrito.controllers.delete;

import MetodologiaDeSistema.Proyecto.feature.carrito.services.interfaces.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrito")
public class EliminarItemController {

    @Autowired
    private CarritoService carritoService;

    @DeleteMapping("/items/{itemId}")
    public void eliminar(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
    }
}