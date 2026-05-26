package com.peluquerias.api.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class citaController {

    private final DataSource dataSource;

    public citaController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testCitas() {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(dataSource);
            List<Map<String, Object>> result = jdbc.queryForList(
            		 "SELECT COUNT(*) as total FROM citas"
            	);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}