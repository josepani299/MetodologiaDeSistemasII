package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.EstadoPedido;

import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ConfirmarPedidoDto;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.PedidoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ProductoMasVendidoDto;

import java.util.List;

public interface PedidoService {
    Long confirmarPedido(Long carritoId, ConfirmarPedidoDto dto);
    void actualizarEstado(Long pedidoId, EstadoPedido nuevoEstado);
    List<PedidoResponseDto> obtenerTodos();

    List<ProductoMasVendidoDto> obtenerProductosMasVendidos(
        int mes,
        int anio
);
}