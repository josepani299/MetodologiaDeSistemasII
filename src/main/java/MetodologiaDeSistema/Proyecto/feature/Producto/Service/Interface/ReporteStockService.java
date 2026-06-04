package MetodologiaDeSistema.Proyecto.feature.Producto.Service.Interface;



import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.ReporteStockMinimoDto;

import java.util.List;

public interface ReporteStockService {

    List<ReporteStockMinimoDto> obtenerReporte(Integer porcentaje);
}