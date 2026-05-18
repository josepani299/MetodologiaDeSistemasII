package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PedidoResponseDto {
    private Long id;
    private String clienteNombre;
    private LocalDateTime fecha;
    private String direccionEnvio;
    private Double total;
    private String estado;
    private String medioPago;
    private List<PedidoItemResponseDto> items;
}