package MetodologiaDeSistema.Proyecto.feature.Direccion.controller.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.request.CrearDireccionRequestDtos;
import MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.response.CrearDireccionResponseDtos;
import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;
import MetodologiaDeSistema.Proyecto.feature.Direccion.services.DireccionEnvioService;
import jakarta.validation.Valid;
import lombok.Builder;

@Builder
@RestController
@RequestMapping("/api/clientes/{clienteId}/direcciones-envio")
@CrossOrigin(origins = "*")
public class DireccionEnvioController {
    
    private static final Logger logger = LoggerFactory.getLogger(DireccionEnvioController.class);
    
    @Autowired
    private DireccionEnvioService direccionEnvioService;
    
    @PostMapping
    public ResponseEntity<CrearDireccionResponseDtos> crearDireccionEnvio(
            @PathVariable Long clienteId,
            @Valid @RequestBody CrearDireccionRequestDtos dto) {
        
        logger.info("POST /api/clientes/{}/direcciones-envio - Request: {}", clienteId, dto);
        
        try {
            DireccionEnvio direccion = direccionEnvioService.crearDireccionEnvio(clienteId, dto);
            
            CrearDireccionResponseDtos response = CrearDireccionResponseDtos.builder()
                .id(direccion.getId())
                .pais(direccion.getPais())
                .provincia(direccion.getProvincia())
                .localidad(direccion.getLocalidad())
                .calle(direccion.getCalle())
                .numeroCalle(direccion.getNumeroCalle())
                .pisoDepto(direccion.getPisoDepto())
                .fechaCreacion(direccion.getFechaCreacion())
                .mensaje("Dirección creada exitosamente")
                .build();
            
            logger.info("Dirección creada: {} para cliente: {}", direccion.getId(), clienteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación: {}", e.getMessage());
            // El GlobalExceptionHandler manejará esto
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al crear dirección", e);
            throw e;
        }
    }
}