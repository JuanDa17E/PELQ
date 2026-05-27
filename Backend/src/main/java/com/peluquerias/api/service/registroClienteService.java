package com.peluquerias.api.service;

import com.peluquerias.api.config.dataSourceConfig;
import com.peluquerias.api.dto.editarClienteRequest;
import com.peluquerias.api.dto.registroClienteRequest;
import com.peluquerias.api.model.cliente;
import com.peluquerias.api.model.rol;
import com.peluquerias.api.model.usuarioCliente;
import com.peluquerias.api.repository.clienteRepository;
import com.peluquerias.api.repository.rolRepository;
import com.peluquerias.api.repository.usuarioClienteRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class registroClienteService {

    private final clienteRepository clienteRepository;
    private final usuarioClienteRepository usuarioClienteRepository;
    private final rolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final dataSourceConfig dataSourceConfig;

    public registroClienteService(clienteRepository clienteRepository,
                                   usuarioClienteRepository usuarioClienteRepository,
                                   rolRepository rolRepository,
                                   PasswordEncoder passwordEncoder,
                                   dataSourceConfig dataSourceConfig) {
        this.clienteRepository = clienteRepository;
        this.usuarioClienteRepository = usuarioClienteRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.dataSourceConfig = dataSourceConfig;
    }

    public List<cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void registrar(registroClienteRequest request) {
        cliente nuevoCliente = new cliente();
        nuevoCliente.setNombreLocal(request.getNombreLocal());
        nuevoCliente.setNombreContacto(request.getNombreContacto());
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setEmail(request.getEmailLocal());
        nuevoCliente.setDbUrl(request.getDbUrl());
        nuevoCliente.setDbUsername(request.getDbUsername());
        nuevoCliente.setDbPassword(request.getDbPassword());
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

        crearUsuarioEnBdCliente(request);

        dataSourceConfig.registrarTenant(
            clienteGuardado.getId().toString(),
            request.getDbUrl(),
            request.getDbUsername(),
            request.getDbPassword()
        );
    }

    @Transactional
    public cliente editar(UUID id, editarClienteRequest request) {
        cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        c.setNombreLocal(request.getNombreLocal());
        c.setNombreContacto(request.getNombreContacto());
        c.setTelefono(request.getTelefono());
        c.setEmail(request.getEmailLocal());
        c.setFechaVencimiento(request.getFechaVencimiento());
        c.setActivo(request.getActivo());
        return clienteRepository.save(c);
    }

    @Transactional
    public void eliminar(UUID id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.deleteById(id);
    }

    @Transactional
    public cliente toggleActivo(UUID id) {
        cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        c.setActivo(!c.getActivo());
        return clienteRepository.save(c);
    }

    private void crearUsuarioEnBdCliente(registroClienteRequest request) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(request.getDbUrl() + "?prepareThreshold=0");
            config.setUsername(request.getDbUsername());
            config.setPassword(request.getDbPassword());
            config.setDriverClassName("org.postgresql.Driver");
            config.setMaximumPoolSize(2);

            try (HikariDataSource ds = new HikariDataSource(config)) {
                JdbcTemplate jdbc = new JdbcTemplate(ds);
                String passwordHash = passwordEncoder.encode(request.getPasswordAdmin());
                jdbc.update(
                    "INSERT INTO usuarios (nombre, email, password_hash, rol_id, activo, created_at) " +
                    "VALUES (?, ?, ?, 2, true, NOW()) ON CONFLICT (email) DO NOTHING",
                    request.getNombreContacto(),
                    request.getEmailAdmin(),
                    passwordHash
                );
            }
        } catch (Exception e) {
            System.err.println("Error al crear usuario en BD cliente: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el usuario en la base de datos del cliente: " + e.getMessage());
        }
    }
}