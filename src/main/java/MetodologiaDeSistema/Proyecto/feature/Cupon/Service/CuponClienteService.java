package MetodologiaDeSistema.Proyecto.feature.Cupon.Service;

import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.ClienteResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Dtos.response.CuponClienteResponseDto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponCliente;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Repository.CuponClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Repository.CuponRepository;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Service.EmailService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuponClienteService {

    @Autowired
    private CuponClienteRepository cuponClienteRepository;

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public List<CuponClienteResponseDto> asignarClientes(Long cuponId, List<Long> clienteIds) {
        Cupon cupon = cuponRepository.findById(cuponId)
                .orElseThrow(() -> new RuntimeException("Cupón no encontrado con ID: " + cuponId));

        List<CuponCliente> cuponesClientes = clienteIds.stream()
                .map(clienteId -> {
                    Cliente cliente = clienteRepository.findById(clienteId)
                            .orElseThrow(() -> new RuntimeException(
                                    "Cliente no encontrado con ID: " + clienteId));

                    if (cuponClienteRepository.existsByCuponIdAndClienteId(cuponId, clienteId)) {
                        throw new RuntimeException(
                                "El cliente ya tiene este cupón asignado");
                    }

                    CuponCliente cuponCliente = new CuponCliente();
                    cuponCliente.setCupon(cupon);
                    cuponCliente.setCliente(cliente);

                    return cuponClienteRepository.save(cuponCliente);
                })
                .collect(Collectors.toList());

        cuponesClientes.forEach(this::enviarEmailCupon);

        return cuponesClientes.stream()
                .map(this::mapearCuponClienteResponse)
                .collect(Collectors.toList());
    }

    public List<CuponClienteResponseDto> obtenerClientesDeCupon(Long cuponId) {
        return cuponClienteRepository.findByCuponId(cuponId).stream()
                .map(this::mapearCuponClienteResponse)
                .collect(Collectors.toList());
    }

    public List<CuponClienteResponseDto> obtenerClientesPendientesDeEmail(Long cuponId) {
        return cuponClienteRepository.findByCuponIdAndEmailEnviadoFalse(cuponId).stream()
                .map(this::mapearCuponClienteResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    private void enviarEmailCupon(CuponCliente cuponCliente) {
        try {
            emailService.enviarEmailCupon(cuponCliente);
            cuponCliente.setEmailEnviado(true);
            cuponCliente.setFechaEnvio(LocalDateTime.now());
            cuponClienteRepository.save(cuponCliente);
        } catch (Exception e) {
            System.err.println("Error enviando email a cliente: " + e.getMessage());
        }
    }

    private CuponClienteResponseDto mapearCuponClienteResponse(CuponCliente cuponCliente) {
        return new CuponClienteResponseDto(
                cuponCliente.getId(),
                cuponCliente.getCupon().getId(),
                cuponCliente.getCupon().getCodigo(),
                new ClienteResponseDto(
                        cuponCliente.getCliente().getId(),
                        cuponCliente.getCliente().getNombre(),
                        cuponCliente.getCliente().getApellido(),
                        cuponCliente.getCliente().getEmail()
                ),
                cuponCliente.isEmailEnviado(),
                cuponCliente.getFechaEnvio(),
                cuponCliente.getFechaAsignacion()
        );
    }
}