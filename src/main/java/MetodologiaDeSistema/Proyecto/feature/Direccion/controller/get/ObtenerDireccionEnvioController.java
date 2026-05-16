

package MetodologiaDeSistema.Proyecto.feature.Direccion.controller.get;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;
import MetodologiaDeSistema.Proyecto.feature.Direccion.services.DireccionEnvioService;

@RestController
@RequestMapping("/api/clientes/{clienteId}/direcciones-envio")
@CrossOrigin(origins = "*")
public class ObtenerDireccionEnvioController {
    
    @Autowired
    private DireccionEnvioService direccionEnvioService;
    
    // GET todas las direcciones de un cliente
    @GetMapping
    public ResponseEntity<List<DireccionEnvio>> obtenerDirecciones(
            @PathVariable Long clienteId) {
        try {
            List<DireccionEnvio> direcciones = direccionEnvioService.obtenerDireccionesPorCliente(clienteId);
            return ResponseEntity.ok(direcciones);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    // GET una dirección específica
    @GetMapping("/{direccionId}")
    public ResponseEntity<DireccionEnvio> obtenerDireccion(
            @PathVariable Long clienteId,
            @PathVariable Long direccionId) {
        try {
            DireccionEnvio direccion = direccionEnvioService.obtenerDireccion(clienteId, direccionId);
            return ResponseEntity.ok(direccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}