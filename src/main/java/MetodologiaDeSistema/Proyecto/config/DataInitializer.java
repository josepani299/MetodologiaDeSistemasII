package MetodologiaDeSistema.Proyecto.config;

import MetodologiaDeSistema.Proyecto.feature.Carrito.Models.Carrito;
import MetodologiaDeSistema.Proyecto.feature.Carrito.Repository.CarritoRepository;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Rol;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
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

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public void run(String... args) {
        if (clienteRepository.findByEmail("admin@admin.com").isEmpty()) {
            Cliente admin = new Cliente();
            admin.setNombre("Admin");
            admin.setApellido("Admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            Carrito carrito = new Carrito();
            admin.setCarrito(carrito);
            clienteRepository.save(admin);
        }

        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto(null, "Pintura Facial Blanca", "Mehron", 1500.0, 20, 5, true));
            productoRepository.save(new Producto(null, "Pintura Facial Negra", "Mehron", 1500.0, 15, 5, true));
            productoRepository.save(new Producto(null, "Pintura Facial Roja", "Snazaroo", 1200.0, 25, 5, true));
            productoRepository.save(new Producto(null, "Pintura Facial Azul", "Snazaroo", 1200.0, 18, 5, true));
            productoRepository.save(new Producto(null, "Pintura UV Verde", "Wolfe", 2200.0, 10, 3, true));
            productoRepository.save(new Producto(null, "Pintura UV Naranja", "Wolfe", 2200.0, 8, 3, true));
            productoRepository.save(new Producto(null, "Set Pinceles Profesional", "Kryolan", 3500.0, 12, 2, true));
            productoRepository.save(new Producto(null, "Esponja Degradado", "Mehron", 800.0, 30, 8, true));
            productoRepository.save(new Producto(null, "Fijador Spray", "Kryolan", 2800.0, 7, 2, true));
            productoRepository.save(new Producto(null, "Removedor Especializado", "Snazaroo", 1100.0, 5, 2, true));
        }
    }
}