package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.Pedido;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.EstadoPedido;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoEmailService {

    private final PedidoRepository pedidoRepository;
    private final RestTemplate restTemplate;

    @Value("${n8n.webhook.confirmacion}")
    private String webhookConfirmacionUrl;

    @Value("${n8n.webhook.cambio-estado}")
    private String webhookCambioEstadoUrl;

    // -------------------------------------------------------------------
    // US03 — Confirmación de pedido
    // -------------------------------------------------------------------
    public void enviarConfirmacion(String email, Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoId));

        Map<String, Object> payload = buildPayload(pedido, email, "CONFIRMACION", null);
        enviarAWebhook(webhookConfirmacionUrl, payload, pedidoId, "confirmación");
    }

    // -------------------------------------------------------------------
    // US08 — Cambio de estado del pedido
    // -------------------------------------------------------------------
    public void enviarCambioEstado(String email, Long pedidoId, String estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoId));

        Map<String, Object> payload = buildPayload(pedido, email, "CAMBIO_ESTADO", estado);
        enviarAWebhook(webhookCambioEstadoUrl, payload, pedidoId, "cambio de estado [" + estado + "]");
    }

    // -------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------

    /**
     * Construye el payload JSON que recibirá el webhook de n8n.
     * Incluye todos los datos necesarios para que la IA genere el email.
     */
    private Map<String, Object> buildPayload(Pedido pedido, String email,
                                             String tipoCaso, String nuevoEstado) {
        Map<String, Object> payload = new HashMap<>();

        // Datos del destinatario
        payload.put("emailDestinatario", email);
        payload.put("nombreCliente", pedido.getCliente().getNombre()); // ajustá según tu entidad

        // Identificación del caso (n8n usa esto para elegir el prompt de IA)
        payload.put("tipoCaso", tipoCaso); // "CONFIRMACION" | "CAMBIO_ESTADO"

        // Datos del pedido
        payload.put("pedidoId", pedido.getId());
        payload.put("estadoActual", pedido.getEstado().name());
        payload.put("total", pedido.getTotal());
        payload.put("medioPago", pedido.getMedioPago().name()); // ajustá si es String
        payload.put("fecha", pedido.getFecha().toString());
        payload.put("direccionEnvio", pedido.getDireccionEnvio());

        // Nuevo estado solo en US08
        if (nuevoEstado != null) {
            payload.put("nuevoEstado", nuevoEstado);
        }

        // Items del pedido: lista de objetos {nombre, cantidad, precioUnitario}
        var items = pedido.getItems().stream().map(item -> {
            Map<String, Object> i = new HashMap<>();
            i.put("nombre", item.getProducto().getNombre()); // ajustá según tu entidad
            i.put("cantidad", item.getCantidad());
            i.put("precioUnitario", item.getSubtotal());
            return i;
        }).toList();
        payload.put("items", items);

        return payload;
    }

    /**
     * Hace la llamada HTTP POST al webhook de n8n.
     * Fire-and-forget: loguea el error pero no propaga la excepción
     * para no interrumpir el flujo principal de negocio.
     */
    private void enviarAWebhook(String url, Map<String, Object> payload,
                                Long pedidoId, String descripcion) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Email de {} encolado correctamente en n8n para pedido #{}", descripcion, pedidoId);
            } else {
                log.warn("n8n respondió con status {} para pedido #{} ({})",
                        response.getStatusCode(), pedidoId, descripcion);
            }
        } catch (Exception ex) {
            // No propagamos: el pedido ya se confirmó/actualizó, el email es asíncrono
            log.error("Error al notificar a n8n para pedido #{} ({}): {}",
                    pedidoId, descripcion, ex.getMessage(), ex);
        }
    }
}