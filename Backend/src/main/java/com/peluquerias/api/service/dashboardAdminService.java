package com.peluquerias.api.service;

import com.peluquerias.api.model.cliente;
import com.peluquerias.api.repository.clienteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class dashboardAdminService {

    private final clienteRepository clienteRepository;

    public dashboardAdminService(clienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Map<String, Object> getDashboard() {
        List<cliente> todos = clienteRepository.findAll();

        long activos   = todos.stream().filter(cliente::getActivo).count();
        long inactivos = todos.stream().filter(c -> !c.getActivo()).count();

        LocalDate hoy      = LocalDate.now();
        LocalDate en30dias = hoy.plusDays(30);

        List<Map<String, Object>> proximosVencer = todos.stream()
            .filter(c -> c.getActivo() &&
                         c.getFechaVencimiento().isAfter(hoy) &&
                         c.getFechaVencimiento().isBefore(en30dias))
            .map(c -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", c.getId());
                m.put("nombreLocal", c.getNombreLocal());
                m.put("fechaVencimiento", c.getFechaVencimiento());
                return m;
            })
            .collect(Collectors.toList());

        List<Map<String, Object>> ultimos = todos.stream()
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
            .limit(5)
            .map(c -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", c.getId());
                m.put("nombreLocal", c.getNombreLocal());
                m.put("nombreContacto", c.getNombreContacto());
                m.put("activo", c.getActivo());
                m.put("createdAt", c.getCreatedAt());
                return m;
            })
            .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("totalActivos", activos);
        response.put("totalInactivos", inactivos);
        response.put("total", todos.size());
        response.put("proximosVencer", proximosVencer);
        response.put("ultimosRegistrados", ultimos);

        return response;
    }
}