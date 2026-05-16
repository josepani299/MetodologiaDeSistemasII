package MetodologiaDeSistema.Proyecto.feature.Direccion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;
import MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.request.CrearDireccionRequestDtos;
import MetodologiaDeSistema.Proyecto.feature.Direccion.models.DireccionEnvio;
import MetodologiaDeSistema.Proyecto.feature.Direccion.repository.DireccionEnvioRepository;
import java.util.List;

@Service
public class DireccionEnvioService {
    
    private static final Logger logger = LoggerFactory.getLogger(DireccionEnvioService.class);
    
    @Autowired
    private DireccionEnvioRepository direccionEnvioRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Transactional
    public DireccionEnvio crearDireccionEnvio(Long clienteId, CrearDireccionRequestDtos dto) {
        logger.info("Iniciando creación de dirección para cliente: {}", clienteId);
        
        // 1. Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> {
                logger.warn("Cliente no encontrado: {}", clienteId);
                return new IllegalArgumentException("Cliente no encontrado");
            });
        
        // 2. Validar que no existe una dirección duplicada
        var direccionDuplicada = direccionEnvioRepository.findByClienteIdAndPaisAndProvinciaAndLocalidadAndCalleAndNumeroCalleAndPisoDepto(
            clienteId,
            dto.getPais(),
            dto.getProvincia(),
            dto.getLocalidad(),
            dto.getCalle(),
            dto.getNumeroCalle(),
            dto.getPisoDepto()
        );
        
        if (direccionDuplicada.isPresent()) {
            logger.warn("Dirección duplicada para cliente: {}", clienteId);
            throw new IllegalArgumentException("Esta dirección ya existe para el cliente");
        }
        
        // 3. Crear y guardar la dirección
        DireccionEnvio direccion = DireccionEnvio.builder()
            .cliente(cliente)
            .pais(dto.getPais())
            .provincia(dto.getProvincia())
            .localidad(dto.getLocalidad())
            .calle(dto.getCalle())
            .numeroCalle(dto.getNumeroCalle())
            .pisoDepto(dto.getPisoDepto())
            .activa(true)
            .build();
        
        DireccionEnvio direccionGuardada = direccionEnvioRepository.save(direccion);
        logger.info("Dirección creada exitosamente para cliente: {} con id: {}", clienteId, direccionGuardada.getId());
        
        return direccionGuardada;
    }

    @Transactional(readOnly = true)
    public List<DireccionEnvio> obtenerDireccionesPorCliente(Long clienteId) {
        logger.info("Obteniendo direcciones para cliente: {}", clienteId);
        
        // Validar cliente existe
        clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        
        return direccionEnvioRepository.findDireccionesActivasPorCliente(clienteId);
    }
    
    @Transactional(readOnly = true)
    public DireccionEnvio obtenerDireccion(Long clienteId, Long direccionId) {
        logger.info("Obteniendo dirección {} para cliente: {}", direccionId, clienteId);
        
        return direccionEnvioRepository.findByClienteIdAndId(clienteId, direccionId)
            .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada o no pertenece a este cliente"));
    }
    
    @Transactional
    public void eliminarDireccion(Long clienteId, Long direccionId) {
        logger.info("Eliminando dirección {} para cliente: {}", direccionId, clienteId);
        
        DireccionEnvio direccion = obtenerDireccion(clienteId, direccionId);
        direccion.setActiva(false);  // Soft delete
        direccionEnvioRepository.save(direccion);
        
        logger.info("Dirección eliminada exitosamente");
    }
    
}

