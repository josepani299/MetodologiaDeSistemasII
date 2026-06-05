package MetodologiaDeSistema.Proyecto.feature.Cupon.Service;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request.CrearCuponMontoRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.request.CrearCuponPorcentajeRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponMontoResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponPorcentajeResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponMonto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponPorcentaje;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Transactional
    public CuponPorcentajeResponseDto crearCuponPorcentaje(
            CrearCuponPorcentajeRequestDto request,
            Long vendedorId) {

        validarFechas(request.getFechaInicio(), request.getFechaVencimiento());

        CuponPorcentaje cupon = new CuponPorcentaje();
        cupon.setFechaInicio(request.getFechaInicio());
        cupon.setFechaVencimiento(request.getFechaVencimiento());
        cupon.setPorcentaje(request.getPorcentaje());
        cupon.setVendedorId(vendedorId);

        CuponPorcentaje cuponGuardado = cuponRepository.save(cupon);

        return new CuponPorcentajeResponseDto(
                cuponGuardado.getId(),
                cuponGuardado.getCodigo(),
                cuponGuardado.getFechaInicio(),
                cuponGuardado.getFechaVencimiento(),
                cuponGuardado.getPorcentaje(),
                cuponGuardado.getVendedorId(),
                cuponGuardado.getFechaCreacion()
        );
    }

    @Transactional
    public CuponMontoResponseDto crearCuponMonto(
            CrearCuponMontoRequestDto request,
            Long vendedorId) {

        validarFechas(request.getFechaInicio(), request.getFechaVencimiento());

        if (request.getMontoDescuento().compareTo(request.getMontoMinimoCompra()) >= 0) {
            throw new IllegalArgumentException(
                    "El descuento no puede ser mayor o igual al monto mínimo de compra");
        }

        CuponMonto cupon = new CuponMonto();
        cupon.setFechaInicio(request.getFechaInicio());
        cupon.setFechaVencimiento(request.getFechaVencimiento());
        cupon.setMontoDescuento(request.getMontoDescuento());
        cupon.setMontoMinimoCompra(request.getMontoMinimoCompra());
        cupon.setVendedorId(vendedorId);

        CuponMonto cuponGuardado = cuponRepository.save(cupon);

        return new CuponMontoResponseDto(
                cuponGuardado.getId(),
                cuponGuardado.getCodigo(),
                cuponGuardado.getFechaInicio(),
                cuponGuardado.getFechaVencimiento(),
                cuponGuardado.getMontoDescuento(),
                cuponGuardado.getMontoMinimoCompra(),
                cuponGuardado.getVendedorId(),
                cuponGuardado.getFechaCreacion()
        );
    }

    public Cupon obtenerCupon(Long id) {
        return cuponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupón no encontrado con ID: " + id));
    }

    public Cupon obtenerCuponPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Cupón no encontrado con código: " + codigo));
    }

    public boolean validarCupon(String codigo) {
        Cupon cupon = obtenerCuponPorCodigo(codigo);
        LocalDate hoy = LocalDate.now();

        return hoy.isAfter(cupon.getFechaInicio().minusDays(1)) &&
                hoy.isBefore(cupon.getFechaVencimiento().plusDays(1));
    }

    public java.util.List<Cupon> obtenerCuponesPorVendedor(Long vendedorId) {
        return cuponRepository.findByVendedorId(vendedorId);
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaVencimiento) {
        if (fechaInicio.isAfter(fechaVencimiento)) {
            throw new IllegalArgumentException(
                    "La fecha de inicio no puede ser posterior a la fecha de vencimiento");
        }

        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de inicio no puede ser anterior a hoy");
        }
    }
}