package MetodologiaDeSistema.Proyecto.feature.Cliente.controller.Get;
import MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.response.RegistroResponseDtos;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Cliente.services.impl.ClientesServicesImplementacion;
//los comentarios que hay en el codigo son para explicar cada anotacion y cada parte del codigo, para que sea mas facil de entender. Obviamente se lo pedi a la IA las explicaciones 🫠
@RestController 
@RequestMapping("/api/clientes/{id}") //esto hace que la ruta para obtener un cliente por id sea /api/clientes/{id}, donde {id} es un placeholder para el id del cliente que queremos obtener. Por ejemplo, si queremos obtener el cliente con id 5, la ruta sería /api/clientes/5.
@RequiredArgsConstructor //esto hace que se inyecte el servicio sin necesidad de escribir un constructor. Inyectar significa que se le da una instancia del servicio a la clase para que pueda usarlo. o sea que el clienteService se inicializa automaticamente con una instancia de ClienteService cuando se crea una instancia de ObtenerClienteController. Esto es parte de la inyección de dependencias que Spring hace por nosotros.
public class ObtenerClienteController {
    private final ClientesServicesImplementacion clienteService;
    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDtos> obtenerCliente(@PathVariable Long id) {
        RegistroResponseDtos cliente = clienteService.obtenerClientePorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
/*
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ListarProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listar(
            @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(productoService.listarProductos(nombre));
    }
}
 */