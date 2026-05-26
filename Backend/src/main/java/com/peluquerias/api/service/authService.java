package com.peluquerias.api.service;

import com.peluquerias.api.dto.loginRequest;
import com.peluquerias.api.dto.loginResponse;
import com.peluquerias.api.model.superadmin;
import com.peluquerias.api.model.usuarioCliente;
import com.peluquerias.api.repository.superadminRepository;
import com.peluquerias.api.repository.usuarioClienteRepository;
import com.peluquerias.api.security.jwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class authService {

    private final superadminRepository superadminRepository;
    private final usuarioClienteRepository usuarioClienteRepository;
    private final jwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public authService(superadminRepository superadminRepository,
                       usuarioClienteRepository usuarioClienteRepository,
                       jwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.superadminRepository = superadminRepository;
        this.usuarioClienteRepository = usuarioClienteRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public loginResponse login(loginRequest request) {

        Optional<superadmin> superadmin = superadminRepository.findByEmail(request.getEmail());
        if (superadmin.isPresent()) {
            if (!passwordEncoder.matches(request.getPassword(), superadmin.get().getPasswordHash())) {
                throw new RuntimeException("Credenciales incorrectas");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", superadmin.get().getId().toString());
            claims.put("rolId", superadmin.get().getRol().getId());
            claims.put("rol", superadmin.get().getRol().getNombre());
            String token = jwtService.generarToken(request.getEmail(), claims);
            return new loginResponse(token, superadmin.get().getRol().getNombre(), "Panel Admin");
        }

        Optional<usuarioCliente> usuarioCliente = usuarioClienteRepository.findByEmail(request.getEmail());
        if (usuarioCliente.isPresent()) {
            if (!passwordEncoder.matches(request.getPassword(), usuarioCliente.get().getPasswordHash())) {
                throw new RuntimeException("Credenciales incorrectas");
            }
            if (!usuarioCliente.get().getCliente().getActivo()) {
                throw new RuntimeException("Suscripción inactiva");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", usuarioCliente.get().getId().toString());
            claims.put("rolId", usuarioCliente.get().getRol().getId());
            claims.put("rol", usuarioCliente.get().getRol().getNombre());
            claims.put("clienteId", usuarioCliente.get().getCliente().getId().toString());
            claims.put("tenantId", usuarioCliente.get().getCliente().getId().toString());
            claims.put("nombreLocal", usuarioCliente.get().getCliente().getNombreLocal());
            String token = jwtService.generarToken(request.getEmail(), claims);
            return new loginResponse(token, usuarioCliente.get().getRol().getNombre(), usuarioCliente.get().getCliente().getNombreLocal());
        }

        throw new RuntimeException("Credenciales incorrectas");
    }
}