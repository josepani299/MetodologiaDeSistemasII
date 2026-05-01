package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ProductoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

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
