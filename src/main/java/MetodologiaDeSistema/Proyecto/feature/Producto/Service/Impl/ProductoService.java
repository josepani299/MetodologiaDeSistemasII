package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;

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
    Producto actualizado = productoRepository.save(producto);

    return mapToDto(actualizado);
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
        return dto;
    }
}
