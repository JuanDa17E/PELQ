package com.peluquerias.api.repository;

import com.peluquerias.api.model.rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface rolRepository extends JpaRepository<rol, Short> {
}