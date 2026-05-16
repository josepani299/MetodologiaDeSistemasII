package MetodologiaDeSistema.Proyecto.feature.Producto.Controllers.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request.ProductoCreateDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl.ProductoService;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
public ProductoResponseDto crearProducto(@RequestBody ProductoCreateDto dto) {
    return productoService.crearProducto(dto, null);
}
}

