package com.peluquerias.api.service;

import com.peluquerias.api.dto.registroClienteRequest;
import com.peluquerias.api.model.cliente;
import com.peluquerias.api.model.rol;
import com.peluquerias.api.model.usuarioCliente;
import com.peluquerias.api.repository.clienteRepository;
import com.peluquerias.api.repository.rolRepository;
import com.peluquerias.api.repository.usuarioClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class registroClienteService {

    private final clienteRepository clienteRepository;
    private final usuarioClienteRepository usuarioClienteRepository;
    private final rolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public registroClienteService(clienteRepository clienteRepository,
                                   usuarioClienteRepository usuarioClienteRepository,
                                   rolRepository rolRepository,
                                   PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.usuarioClienteRepository = usuarioClienteRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrar(registroClienteRequest request) {

        cliente nuevoCliente = new cliente();
        nuevoCliente.setNombreLocal(request.getNombreLocal());
        nuevoCliente.setNombreContacto(request.getNombreContacto());
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setEmail(request.getEmailLocal());
        nuevoCliente.setDbUrl(request.getDbUrl());
        nuevoCliente.setActivo(true);
        nuevoCliente.setFechaInicio(LocalDate.now());
        nuevoCliente.setFechaVencimiento(request.getFechaVencimiento());
        nuevoCliente.setCreatedAt(LocalDateTime.now());

        cliente clienteGuardado = clienteRepository.save(nuevoCliente);

        rol rolAdmin = rolRepository.findById((short) 2)
                .orElseThrow(() -> new RuntimeException("Rol admin no encontrado"));

        usuarioCliente nuevoUsuario = new usuarioCliente();
        nuevoUsuario.setCliente(clienteGuardado);
        nuevoUsuario.setEmail(request.getEmailAdmin());
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(request.getPasswordAdmin()));
        nuevoUsuario.setRol(rolAdmin);
        nuevoUsuario.setCreatedAt(LocalDateTime.now());

        usuarioClienteRepository.save(nuevoUsuario);
    }
}