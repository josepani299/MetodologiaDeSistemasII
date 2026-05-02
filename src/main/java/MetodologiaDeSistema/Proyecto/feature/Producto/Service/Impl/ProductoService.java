package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request.ProductoCreateDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    
        if (imagen != null && !imagen.isEmpty()) {
            try {
                producto.setImagen(imagen.getBytes()); 
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen");
            }
        }

        Producto guardado = productoRepository.save(producto);

        return mapToDto(guardado);
    }

    public List<ProductoResponseDto> listarProductos(String nombre) {
        List<Producto> productos = (nombre != null && !nombre.isBlank())
                ? productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre)
                : productoRepository.findByActivoTrue();

        return productos.stream().map(this::mapToDto).toList();
    }

    private ProductoResponseDto mapToDto(Producto p) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setMarca(p.getMarca());
        dto.setPrecio(p.getPrecio());
        dto.setStockActual(p.getStockActual());
        dto.setStockBajo(p.getStockActual() <= p.getStockMinimo());
        return dto;
    }
}
