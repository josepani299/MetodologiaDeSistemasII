package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Impl;


import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ReporteStockMinimoDto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;

import MetodologiaDeSistema.Proyecto.feature.Producto.Service.Interface.ReporteStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteStockServiceImpl implements ReporteStockService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ReporteStockMinimoDto> obtenerReporte(Integer porcentaje) {

        List<Producto> productos = productoRepository.findAll();

        List<ReporteStockMinimoDto> reporte = new ArrayList<>();

        for (Producto producto : productos) {

            if (producto.getClass().getSimpleName().equals("Kit")) {
                continue;
            }

            double limite =
                    producto.getStockMinimo() +
                            (producto.getStockMinimo() * (porcentaje / 100.0));

            if (producto.getStockActual() <= limite) {

                reporte.add(
                        new ReporteStockMinimoDto(
                                producto.getId(),
                                producto.getNombre(),
                                producto.getStockActual(),
                                producto.getStockMinimo()
                        )
                );
            }
        }

        return reporte;
    }
}