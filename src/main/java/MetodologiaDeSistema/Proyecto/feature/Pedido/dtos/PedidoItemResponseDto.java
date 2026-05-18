package MetodologiaDeSistema.Proyecto.feature.Pedido.dtos;

import lombok.Data;

@Data
public class PedidoItemResponseDto {
    private Long id;
    private String productoNombre;
    private Integer cantidad;
    private Double subtotal;
}