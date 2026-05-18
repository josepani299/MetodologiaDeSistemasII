package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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

    public ProductoResponseDto crearProducto(ProductoCreateDto dto, MultipartFile imagen) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setMarca(dto.getMarca());
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
        dto.setPrecio(p.getPrecio());
        dto.setStockActual(p.getStockActual());
        dto.setStockMinimo(p.getStockMinimo());
        dto.setStockBajo(p.getStockActual() <= p.getStockMinimo());
        dto.setActivo(p.isActivo());
        dto.setImagenUrl(p.getImagenUrl());
        return dto;
    }
}