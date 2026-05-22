package com.peluquerias.api.controller;

import com.peluquerias.api.model.cliente;
import com.peluquerias.api.repository.clienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class clienteController {

    private final clienteRepository clienteRepository;

    public clienteController(clienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<cliente>> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }
}