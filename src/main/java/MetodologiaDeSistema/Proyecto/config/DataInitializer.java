package MetodologiaDeSistema.Proyecto.config;

import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (clienteRepository.findByEmail("admin@admin.com").isEmpty()) {
            Cliente admin = new Cliente();
            admin.setNombre("Admin");
            admin.setApellido("Admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            clienteRepository.save(admin);
        }
    }
}