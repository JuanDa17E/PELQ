package com.peluquerias.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class editarClienteRequest {

    @NotBlank
    private String nombreLocal;

    @NotBlank
    private String nombreContacto;

    private String telefono;

    @Email
    private String emailLocal;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotNull
    private Boolean activo;
}