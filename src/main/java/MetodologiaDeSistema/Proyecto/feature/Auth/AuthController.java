package MetodologiaDeSistema.Proyecto.feature.Auth;

import MetodologiaDeSistema.Proyecto.config.JwtService;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElse(null);

        if (cliente == null || !passwordEncoder.matches(password, cliente.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

        String token = jwtService.generarToken(cliente.getEmail(), cliente.getRol().name());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", cliente.getRol().name(),
                "id", cliente.getId(),
                "nombre", cliente.getNombre()
        ));
    }
}