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
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRol(Rol.ADMIN);
            Carrito carrito = new Carrito();
            admin.setCarrito(carrito);
            clienteRepository.save(admin);
        }

        if (clienteRepository.findByEmail("vendedor@vendedor.com").isEmpty()) {
            Cliente vendedor = new Cliente();
            vendedor.setNombre("Vendedor");
            vendedor.setApellido("Demo");
            vendedor.setEmail("vendedor@vendedor.com");
            vendedor.setPassword(passwordEncoder.encode("password"));
            vendedor.setRol(Rol.VENDEDOR);
            Carrito carrito = new Carrito();
            vendedor.setCarrito(carrito);
            clienteRepository.save(vendedor);
        }

        if (productoRepository.count() == 0) {
            // Pintura Facial Blanca — hombre con pintura blanca en el rostro (body painting)
            productoRepository.save(new Producto(null, "Pintura Facial Blanca", "Mehron",
                    "Pintura facial de alta cobertura, ideal para diseños artísticos y efectos especiales. Fórmula suave apta para piel sensible.",
                    1500.0, 20, 5, true,
                    "https://images.unsplash.com/photo-1551944636-a2b3624bd216?w=400&fit=crop"));

            // Pintura Facial Negra — hombre con body paint negro dramático
            productoRepository.save(new Producto(null, "Pintura Facial Negra", "Mehron",
                    "Pintura negra intensa para contornos y detalles precisos. Larga duración y fácil remoción con agua.",
                    1500.0, 15, 5, true,
                    "https://images.unsplash.com/photo-1516651000622-7f32fe80a57a?w=400&fit=crop"));

            // Pintura Facial Roja — hombre con pintura roja en rostro y manos (body painting)
            productoRepository.save(new Producto(null, "Pintura Facial Roja", "Snazaroo",
                    "Pintura roja vibrante certificada dermatológicamente. Perfecta para festivales, teatro y eventos artísticos.",
                    1200.0, 25, 5, true,
                    "https://images.unsplash.com/photo-1682965636199-091797901722?w=400&fit=crop"));

            // Pintura Facial Azul — hombre cubierto de pintura azul (body painting)
            productoRepository.save(new Producto(null, "Pintura Facial Azul", "Snazaroo",
                    "Tono azul brillante de alta pigmentación. Se mezcla fácilmente con otros colores para crear efectos únicos.",
                    1200.0, 18, 5, true,
                    "https://images.unsplash.com/photo-1682965636819-d3caf1da767e?w=400&fit=crop"));

            // Pintura UV Verde — mujer con body paint que brilla bajo luz negra (UV/blacklight)
            productoRepository.save(new Producto(null, "Pintura UV Verde", "Wolfe",
                    "Pintura fluorescente que brilla bajo luz ultravioleta. Ideal para fiestas, eventos nocturnos y shows artísticos.",
                    2200.0, 10, 3, true,
                    "https://images.unsplash.com/photo-1539035992980-e41ff3f540ed?w=400&fit=crop"));

            // Pintura UV Naranja — ilusión de body painting con colores vivos
            productoRepository.save(new Producto(null, "Pintura UV Naranja", "Wolfe",
                    "Pintura UV naranja de alta intensidad. Efecto fluorescente espectacular bajo luz negra, segura para la piel.",
                    2200.0, 8, 3, true,
                    "https://images.unsplash.com/photo-1574449589531-0676e58e8ce7?w=400&fit=crop"));

            // Set Pinceles Profesional — set de pinceles de maquillaje artístico profesional
            productoRepository.save(new Producto(null, "Set Pinceles Profesional", "Kryolan",
                    "Set de 12 pinceles profesionales de distintos tamaños para detalles finos, rellenos y degradados en body painting.",
                    3500.0, 12, 2, true,
                    "https://images.unsplash.com/photo-1535415493710-bdf0b2dc725e?w=400&fit=crop"));

            // Esponja Degradado — resultado de técnica de esponja: degradado multicolor en rostro
            productoRepository.save(new Producto(null, "Esponja Degradado", "Mehron",
                    "Esponja de alta densidad para lograr degradados y difuminados perfectos. Reutilizable y fácil de limpiar.",
                    800.0, 30, 8, true,
                    "https://images.unsplash.com/photo-1613520771675-a8acb52bc78b?w=400&fit=crop"));

            // Fijador Spray — frasco spray de cosméticos profesional
            productoRepository.save(new Producto(null, "Fijador Spray", "Kryolan",
                    "Fijador en spray de larga duración para proteger y sellar el maquillaje artístico. Resistente al agua y al sudor.",
                    2800.0, 7, 2, true,
                    "https://images.unsplash.com/photo-1627885793933-584e53987c14?w=400&fit=crop"));

            // Removedor Especializado — removedor de maquillaje en frasco negro profesional
            productoRepository.save(new Producto(null, "Removedor Especializado", "Snazaroo",
                    "Removedor suave especialmente formulado para eliminar pinturas corporales sin irritar la piel. Enriquecido con aloe vera.",
                    1100.0, 5, 2, true,
                    "https://images.unsplash.com/photo-1618331642909-d2ae493c2dda?w=400&fit=crop"));
        }
    }
}