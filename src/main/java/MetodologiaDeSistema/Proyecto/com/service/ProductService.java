package MetodologiaDeSistema.Proyecto.com.service;


public class ProductService {
    import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDto dto, MultipartFile image) {

        // =====================
        // 🧪 VALIDACIONES
        // =====================

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (dto.getMarca() == null || dto.getMarca().isBlank()) {
            throw new RuntimeException("La marca es obligatoria");
        }

        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser positivo");
        }

        if (dto.getStockActual() == null || dto.getStockActual() < 0) {
            throw new RuntimeException("El stock actual debe ser positivo");
        }

        if (dto.getStockMinimo() == null || dto.getStockMinimo() < 0) {
            throw new RuntimeException("El stock mínimo debe ser positivo");
        }

        // =====================
        // 🖼️ VALIDAR IMAGEN
        // =====================

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("La imagen es obligatoria");
        }

        String contentType = image.getContentType();

        if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new RuntimeException("Solo se permiten imágenes JPG o PNG");
        }

        // =====================
        // 💾 GUARDAR IMAGEN
        // =====================

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(UPLOAD_DIR + fileName);
            image.transferTo(file);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen");
        }

        // =====================
        // 🧱 CREAR PRODUCTO
        // =====================

        Product product = new Product();
        product.setNombre(dto.getNombre());
        product.setMarca(dto.getMarca());
        product.setPrecio(dto.getPrecio());
        product.setStockActual(dto.getStockActual());
        product.setStockMinimo(dto.getStockMinimo());
        product.setImagenUrl("/uploads/" + fileName);

        // =====================
        // 🗄️ GUARDAR EN BD
        // =====================

        return productRepository.save(product);
    }
}
}
