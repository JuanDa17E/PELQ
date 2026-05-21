package com.peluquerias.api.controller;

import com.peluquerias.api.dto.loginRequest;
import com.peluquerias.api.dto.loginResponse;
import com.peluquerias.api.service.authService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class authController {

    private final authService authService;

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@Valid @RequestBody loginRequest request) {
        try {
            loginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }
}