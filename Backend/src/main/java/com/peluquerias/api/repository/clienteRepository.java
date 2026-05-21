package com.peluquerias.api.repository;

import com.peluquerias.api.model.cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface clienteRepository extends JpaRepository<cliente, UUID> {
}