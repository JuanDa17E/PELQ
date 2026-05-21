package com.peluquerias.api.service;

import com.peluquerias.api.dto.loginRequest;
import com.peluquerias.api.dto.loginResponse;
import com.peluquerias.api.model.superadmin;
import com.peluquerias.api.model.usuarioCliente;
import com.peluquerias.api.repository.superadminRepository;
import com.peluquerias.api.repository.usuarioClienteRepository;
import com.peluquerias.api.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class authService {

    private final superadminRepository superadminRepository;
    private final usuarioClienteRepository usuarioClienteRepository;
    private final jwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public loginResponse login(loginRequest request) {

        // Primero busca en superadmin
        Optional<superadmin> superadmin = superadminRepository.findByEmail(request.getEmail());
        if (superadmin.isPresent()) {
            if (!passwordEncoder.matches(request.getPassword(), superadmin.get().getPasswordHash())) {
                throw new RuntimeException("Credenciales incorrectas");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("rol", "superadmin");
            String token = jwtService.generarToken(request.getEmail(), claims);
            return new loginResponse(token, "superadmin", "Panel Admin");
        }

        // Si no es superadmin busca en usuarios_clientes
        Optional<usuarioCliente> usuarioCliente = usuarioClienteRepository.findByEmail(request.getEmail());
        if (usuarioCliente.isPresent()) {
            if (!passwordEncoder.matches(request.getPassword(), usuarioCliente.get().getPasswordHash())) {
                throw new RuntimeException("Credenciales incorrectas");
            }
            if (!usuarioCliente.get().getCliente().getActivo()) {
                throw new RuntimeException("Suscripción inactiva");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("rol", "admin");
            claims.put("clienteId", usuarioCliente.get().getCliente().getId().toString());
            claims.put("dbUrl", usuarioCliente.get().getCliente().getDbUrl());
            claims.put("nombreLocal", usuarioCliente.get().getCliente().getNombreLocal());
            String token = jwtService.generarToken(request.getEmail(), claims);
            return new loginResponse(token, "admin", usuarioCliente.get().getCliente().getNombreLocal());
        }

        throw new RuntimeException("Credenciales incorrectas");
    }
}