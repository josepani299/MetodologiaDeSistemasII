package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import MetodologiaDeSistema.Proyecto.feature.Pedido.dtos.ConfirmarPedidoDto;

public interface PedidoService {

    void confirmarPedido(Long carritoId, ConfirmarPedidoDto dto);
}