package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import org.springframework.stereotype.Service;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.EstadoPedido;

@Service
public class PedidoEmailService {

    public void enviarConfirmacion(
            String email,
            Long pedidoId) {

        System.out.println(
                "Email enviado a "
                        + email +
                        " del pedido "
                        + pedidoId
        );
    }

    public void enviarCambioEstado(
            String email,
            Long pedidoId,
            EstadoPedido estado) {

        System.out.println(
                "Email enviado a "
                        + email +
                        " del pedido "
                        + pedidoId +
                        " nuevo estado: "
                        + estado
        );
    }
}