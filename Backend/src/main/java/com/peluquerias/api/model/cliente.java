package com.peluquerias.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre_local", nullable = false)
    private String nombreLocal;

    @Column(name = "nombre_contacto", nullable = false)
    private String nombreContacto;

    private String telefono;

    private String email;

    @Column(name = "db_url", nullable = false)
    private String dbUrl;
    
    @Column(name = "db_username")
    private String dbUsername;

    @Column(name = "db_password")
    private String dbPassword;
    
    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}