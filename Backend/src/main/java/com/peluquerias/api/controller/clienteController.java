package com.peluquerias.api.controller;

import com.peluquerias.api.dto.editarClienteRequest;
import com.peluquerias.api.dto.registroClienteRequest;
import com.peluquerias.api.model.cliente;
import com.peluquerias.api.service.registroClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class clienteController {

    private final registroClienteService registroClienteService;

    public clienteController(registroClienteService registroClienteService) {
        this.registroClienteService = registroClienteService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<cliente>> listarClientes() {
        return ResponseEntity.ok(registroClienteService.listarTodos());
    }

    @PostMapping("/clientes")
    public ResponseEntity<Void> registrarCliente(@Valid @RequestBody registroClienteRequest request) {
        registroClienteService.registrar(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<cliente> editarCliente(@PathVariable UUID id,
                                                  @Valid @RequestBody editarClienteRequest request) {
        return ResponseEntity.ok(registroClienteService.editar(id, request));
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable UUID id) {
        registroClienteService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/clientes/{id}/toggle")
    public ResponseEntity<cliente> toggleActivo(@PathVariable UUID id) {
        return ResponseEntity.ok(registroClienteService.toggleActivo(id));
    }
}