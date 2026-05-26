package com.peluquerias.api.config;

import com.peluquerias.api.security.jwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class tenantFilter extends OncePerRequestFilter {

    private final jwtService jwtService;

    public tenantFilter(jwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtService.validarToken(token)) {
                    String tenantId = jwtService.extraerClaim(token, "tenantId");
                    System.out.println("=== tenantFilter === tenantId: " + tenantId);
                    tenantContext.set(tenantId != null ? tenantId : "admin");
                } else {
                    tenantContext.set("admin");
                }
            } else {
                tenantContext.set("admin");
            }
            filterChain.doFilter(request, response);
        } finally {
            tenantContext.clear();
        }
    }
}