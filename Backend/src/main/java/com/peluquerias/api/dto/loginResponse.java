package com.peluquerias.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginResponse {
    private String token;
    private String rol;
    private String nombreLocal;
}