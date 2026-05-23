package MetodologiaDeSistema.Proyecto.feature.Auth;

import MetodologiaDeSistema.Proyecto.config.JwtService;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        System.out.println("EMAIL: " + email);
        System.out.println("PASSWORD: " + password);

        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);



        if (cliente == null || !passwordEncoder.matches(password, cliente.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

        String token = jwtService.generarToken(cliente.getEmail(), cliente.getRol().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("rol", cliente.getRol().name());
        response.put("id", cliente.getId());
        response.put("nombre", cliente.getNombre());
        response.put("carritoId", cliente.getCarrito() != null ? cliente.getCarrito().getId() : null);
        return ResponseEntity.ok(response);
    }


}