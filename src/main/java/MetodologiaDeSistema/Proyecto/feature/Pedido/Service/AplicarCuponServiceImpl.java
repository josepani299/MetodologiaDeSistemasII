package MetodologiaDeSistema.Proyecto.feature.Pedido.Service;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponCliente;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponMonto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponPorcentaje;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Repository.CuponClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Repository.CuponRepository;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Models.Pedido;
import MetodologiaDeSistema.Proyecto.feature.Pedido.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AplicarCuponServiceImpl implements AplicarCuponService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private CuponClienteRepository cuponClienteRepository;

    @Override
    @Transactional
    public Double aplicarCupon(Long pedidoId, Long clienteId, String codigoCupon) {

        // 1. Buscar el pedido
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        // 2. Buscar el cupón por código
        Cupon cupon = cuponRepository.findByCodigo(codigoCupon)
                .orElseThrow(() -> new IllegalArgumentException("Código de cupón inválido"));

        // 3. Verificar que el cupón pertenece al cliente
        CuponCliente cuponCliente = cuponClienteRepository
                .findByCuponAndClienteId(cupon, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cupón no pertenece al cliente"));

        // 4. Verificar que el cupón no esté usado
        if (cuponCliente.isUsado()) {
            throw new IllegalArgumentException("El cupón ya fue utilizado");
        }

        // 5. Verificar que el cupón no esté vencido
        if (cupon.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El cupón está vencido");
        }

        // 6. Calcular el descuento según el tipo de cupón y validar monto
        Double totalActual = pedido.getTotal();
        Double totalConDescuento;

        if (cupon instanceof CuponMonto cuponMonto) {
            BigDecimal montoDescuento = cuponMonto.getMontoDescuento();
            BigDecimal montoMinimo = cuponMonto.getMontoMinimoCompra();

            // El total del pedido debe ser MAYOR al monto mínimo de compra
            if (BigDecimal.valueOf(totalActual).compareTo(montoMinimo) <= 0) {
                throw new IllegalArgumentException(
                        "El total del pedido debe ser mayor al monto mínimo requerido por el cupón");
            }

            totalConDescuento = totalActual - montoDescuento.doubleValue();

        } else if (cupon instanceof CuponPorcentaje cuponPorcentaje) {
            BigDecimal porcentaje = cuponPorcentaje.getPorcentaje();
            double descuento = totalActual * porcentaje.doubleValue() / 100;

            // El descuento no puede ser mayor o igual al total
            if (descuento >= totalActual) {
                throw new IllegalArgumentException(
                        "El descuento no puede ser mayor o igual al total del pedido");
            }

            totalConDescuento = totalActual - descuento;

        } else {
            throw new IllegalArgumentException("Tipo de cupón no soportado");
        }

        // 7. Aplicar el descuento al pedido
        pedido.setTotal(totalConDescuento);
        pedidoRepository.save(pedido);

        // 8. Marcar el cupón como usado
        cuponCliente.setUsado(true);
        cuponCliente.setFechaUso(LocalDateTime.now());
        cuponClienteRepository.save(cuponCliente);

        return totalConDescuento;
    }
}