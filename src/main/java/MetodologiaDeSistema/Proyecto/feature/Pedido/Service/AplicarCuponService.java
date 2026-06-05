package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

public interface AplicarCuponService {
    Double aplicarCupon(Long pedidoId, Long clienteId, String codigoCupon);
}