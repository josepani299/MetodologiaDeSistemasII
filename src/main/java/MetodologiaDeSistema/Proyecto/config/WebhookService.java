package MetodologiaDeSistema.Proyecto.config;

import MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response.StockCriticoResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    private final WebClient webClient = WebClient.create();

    @Value("${n8n.webhook.stock-critico}")
    private String webhookUrl;

    @Async
    public void dispararWebhookStockCritico(List<StockCriticoResponseDto> productos) {
        try {
            webClient.post()
                    .uri(webhookUrl)
                    .bodyValue(productos)
                    .retrieve()
                    .toBodilessEntity()
                    .subscribe(
                            response -> log.info("Webhook n8n disparado OK — status: {}", response.getStatusCode()),
                            error -> log.warn("Webhook n8n falló (no bloqueante): {}", error.getMessage())
                    );
        } catch (Exception e) {
            log.warn("No se pudo disparar el webhook de n8n: {}", e.getMessage());
        }
    }
}