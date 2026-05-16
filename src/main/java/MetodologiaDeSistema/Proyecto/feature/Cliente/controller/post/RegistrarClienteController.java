package MetodologiaDeSistema.Proyecto.feature.Cliente.controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.request.RegistroRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.response.RegistroResponseDtos;
import MetodologiaDeSistema.Proyecto.feature.Cliente.services.impl.ClientesServicesImplementacion;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class RegistrarClienteController {
    @Autowired
    private ClientesServicesImplementacion clienteService;

     @PostMapping("/registro")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody RegistroRequestDto registroRequestDto) {
        try {
            RegistroResponseDtos respuesta = clienteService.registrarCliente(registroRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (RuntimeException e) {
            // Manejo de errores de negocio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            // Manejo de errores generales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
        public static class ErrorResponseDto {
        public String mensaje;
        public int codigo;
        
        public ErrorResponseDto(String mensaje, int codigo) {
            this.mensaje = mensaje;
            this.codigo = codigo;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public int getCodigo() {
            return codigo;
        }
    }

}
