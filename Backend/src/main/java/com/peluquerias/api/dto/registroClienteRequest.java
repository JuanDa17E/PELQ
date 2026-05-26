package com.peluquerias.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class registroClienteRequest {

    @NotBlank
    private String nombreLocal;

    @NotBlank
    private String nombreContacto;

    private String telefono;

    @Email
    private String emailLocal;

    @NotBlank
    private String dbUrl;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotBlank
    @Email
    private String emailAdmin;

    @NotBlank
    private String passwordAdmin;
}