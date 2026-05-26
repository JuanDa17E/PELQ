//package com.peluquerias.api.controller;
//
//import com.peluquerias.api.config.tenantContext;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/test")
//@CrossOrigin(origins = "*")
//public class testController {
//
//    @GetMapping("/conexion")
//    public ResponseEntity<Map<String, String>> verificarConexion() {
//        String dbActual = tenantContext.getDbUrl();
//        return ResponseEntity.ok(Map.of(
//            "baseDatos", dbActual != null ? dbActual : "admin",
//            "mensaje", dbActual != null ? "Conectado a base de datos del cliente" : "Conectado a base de datos admin"
//        ));
//    }
//}