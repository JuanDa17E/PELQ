package com.peluquerias.api.repository;

import com.peluquerias.api.model.superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface superadminRepository extends JpaRepository<superadmin, UUID> {
    Optional<superadmin> findByEmail(String email);
}