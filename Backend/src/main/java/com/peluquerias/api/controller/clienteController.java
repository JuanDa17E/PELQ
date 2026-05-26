package com.peluquerias.api.controller;

import com.peluquerias.api.dto.registroClienteRequest;
import com.peluquerias.api.model.cliente;
import com.peluquerias.api.repository.clienteRepository;
import com.peluquerias.api.service.registroClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class clienteController {

    private final clienteRepository clienteRepository;
    private final registroClienteService registroClienteService;

    public clienteController(clienteRepository clienteRepository,
                              registroClienteService registroClienteService) {
        this.clienteRepository = clienteRepository;
        this.registroClienteService = registroClienteService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<cliente>> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping("/clientes")
    public ResponseEntity<Void> registrarCliente(@Valid @RequestBody registroClienteRequest request) {
        registroClienteService.registrar(request);
        return ResponseEntity.ok().build();
    }
}