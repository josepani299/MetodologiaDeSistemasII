package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import org.springframework.stereotype.Service;

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
}