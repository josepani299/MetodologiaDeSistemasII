package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import MetodologiaDeSistema.Proyecto.config.WebhookService;
import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.StockCriticoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request.ProductoCreateDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions.ProductoEnKitActivoException;
import MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions.ProductoNoEncontradoException;
import MetodologiaDeSistema.Proyecto.feature.Producto.Exceptions.ProductoYaInactivoException;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final WebhookService webhookService;

    public ProductoResponseDto crearProducto(ProductoCreateDto dto, MultipartFile imagen) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setMarca(dto.getMarca());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStockActual(dto.getStockActual());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setActivo(true);

        Producto guardado = productoRepository.save(producto);
        return mapToDto(guardado);
    }

    public List<ProductoResponseDto> listarProductos(String nombre) {
        List<Producto> productos = (nombre != null && !nombre.isBlank())
                ? productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre)
                : productoRepository.findByActivoTrue();

        return productos.stream().map(this::mapToDto).toList();
    }

    public ProductoResponseDto darDeBaja(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        if (!producto.isActivo()) {
            throw new ProductoYaInactivoException(id);
        }

        if (productoRepository.existeEnKitActivo(id)) {
            throw new ProductoEnKitActivoException(id);
        }

        producto.setActivo(false);
        return mapToDto(productoRepository.save(producto));
    }

    public ProductoResponseDto importarImagen(Long id, MultipartFile imagen) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        if (producto.getImagenUrl() != null && !producto.getImagenUrl().isBlank()) {
            throw new RuntimeException("El producto ya tiene una imagen asignada.");
        }

        try {
            String base64 = Base64.getEncoder().encodeToString(imagen.getBytes());
            String mediaType = imagen.getContentType();
            producto.setImagenUrl("data:" + mediaType + ";base64," + base64);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen.");
        }

        return mapToDto(productoRepository.save(producto));
    }

    private ProductoResponseDto mapToDto(Producto p) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setMarca(p.getMarca());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStockActual(p.getStockActual());
        dto.setStockMinimo(p.getStockMinimo());
        dto.setStockBajo(p.getStockActual() <= p.getStockMinimo());
        dto.setActivo(p.isActivo());
        dto.setImagenUrl(p.getImagenUrl());
        return dto;
    }

    @Value("${features.stock-critico.mock:true}")
    private boolean useMock;

    public List<StockCriticoResponseDto> getStockCritico() {
        List<StockCriticoResponseDto> productos = useMock
                ? getMockStockCritico()
                : getRealStockCritico();

        webhookService.dispararWebhookStockCritico(productos);

        return productos;
    }

    private List<StockCriticoResponseDto> getRealStockCritico() {
        LocalDateTime hace3Meses = LocalDateTime.now().minusMonths(3);
        return productoRepository.findStockCriticoConVentas(hace3Meses);
    }

    private List<StockCriticoResponseDto> getMockStockCritico() {
        return List.of(
                new StockCriticoResponseDto(1L, "Auriculares Bluetooth Pro", "Sony",     2, 5,  47L),
                new StockCriticoResponseDto(2L, "Teclado Mecánico RGB",      "Logitech", 1, 10, 63L),
                new StockCriticoResponseDto(3L, "Mouse Inalámbrico",         "Razer",    0, 8,  38L),
                new StockCriticoResponseDto(4L, "Webcam Full HD 1080p",      "Logitech", 3, 5,  29L),
                new StockCriticoResponseDto(5L, "Hub USB-C 7 Puertos",       "Anker",    2, 6,  51L)
        );
    }


}