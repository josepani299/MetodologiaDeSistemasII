package MetodologiaDeSistema.Proyecto.feature.Cliente.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.request.RegistroRequestDto;
import MetodologiaDeSistema.Proyecto.feature.Cliente.dtos.response.RegistroResponseDtos;
import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;
import MetodologiaDeSistema.Proyecto.feature.Cliente.repositories.ClienteRepository;

@Service
public class ClientesServicesImplementacion {
    
    @Autowired
    private ClienteRepository clienterepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistroResponseDtos registrarCliente(RegistroRequestDto registroRequestDto){
            
        if (clienterepository.existsByEmail(registroRequestDto.getEmail())) {
           throw new RuntimeException("El email ya está registrado en el sistema");
         }
        
         // Crear nueva instancia de Cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(registroRequestDto.getNombre());
        nuevoCliente.setApellido(registroRequestDto.getApellido());
        nuevoCliente.setEmail(registroRequestDto.getEmail());

        // Encriptar la contraseña antes de guardarla
        nuevoCliente.setPassword(passwordEncoder.encode(registroRequestDto.getPassword()));

        // Asignar la dirección
        nuevoCliente.setDireccion(registroRequestDto.getDireccion());

        // Guardar en la base de datos
        Cliente clienteGuardado = clienterepository.save(nuevoCliente);

        RegistroResponseDtos respuesta = new RegistroResponseDtos();
        respuesta.setId(clienteGuardado.getId());
        respuesta.setNombre(clienteGuardado.getNombre());
        respuesta.setApellido(clienteGuardado.getApellido());
        respuesta.setEmail(clienteGuardado.getEmail());
        respuesta.setMensaje("Registro exitoso. ¡Bienvenido a nuestra plataforma!");
        respuesta.setExitoso(true);
        respuesta.setFechaRegistro(LocalDateTime.now());

        return respuesta;


    }
public RegistroResponseDtos obtenerClientePorId(Long id) {
    Cliente cliente = clienterepository.findById(id).orElse(null);
    if (cliente != null) {
        RegistroResponseDtos respuesta = new RegistroResponseDtos();
        respuesta.setId(cliente.getId());
        respuesta.setNombre(cliente.getNombre());
        respuesta.setApellido(cliente.getApellido());
        respuesta.setEmail(cliente.getEmail());
        respuesta.setMensaje("Cliente encontrado exitosamente.");
        respuesta.setExitoso(true);
        return respuesta;
    } else {
        return null; // O puedes lanzar una excepción personalizada si prefieres
    }
}
}
