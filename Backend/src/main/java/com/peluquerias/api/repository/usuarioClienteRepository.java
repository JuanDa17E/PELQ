package com.peluquerias.api.repository;

import com.peluquerias.api.model.usuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface usuarioClienteRepository extends JpaRepository<usuarioCliente, UUID> {
    Optional<usuarioCliente> findByEmail(String email);
}