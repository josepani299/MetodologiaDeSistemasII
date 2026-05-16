package MetodologiaDeSistema.Proyecto.feature.Carrito.controllers.get;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class GetCarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @GetMapping("/{id}")
    public Carrito obtenerCarrito(@PathVariable Long id) {

        return carritoRepository.findById(id).orElseThrow();
    }
}