package com.peluquerias.api.controller;

import com.peluquerias.api.service.dashboardAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "*")
public class dashboardAdminController {

    private final dashboardAdminService dashboardAdminService;

    public dashboardAdminController(dashboardAdminService dashboardAdminService) {
        this.dashboardAdminService = dashboardAdminService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(dashboardAdminService.getDashboard());
    }
}