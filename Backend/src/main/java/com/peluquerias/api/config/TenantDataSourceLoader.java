package com.peluquerias.api.config;

import com.peluquerias.api.model.cliente;
import com.peluquerias.api.repository.clienteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TenantDataSourceLoader {

    private final clienteRepository clienteRepository;
    private final dataSourceConfig dataSourceConfig;

    public TenantDataSourceLoader(clienteRepository clienteRepository,
                                   dataSourceConfig dataSourceConfig) {
        this.clienteRepository = clienteRepository;
        this.dataSourceConfig = dataSourceConfig;
    }

    @PostConstruct
    public void cargarTenants() {
        tenantContext.set("admin");
        try {
            for (cliente c : clienteRepository.findAll()) {
                if (c.getDbUrl() != null && c.getActivo() 
                    && c.getDbUsername() != null && c.getDbPassword() != null) {
                    dataSourceConfig.registrarTenant(
                        c.getId().toString(),
                        c.getDbUrl(),
                        c.getDbUsername(),
                        c.getDbPassword()
                    );
                    System.out.println("Tenant cargado: " + c.getNombreLocal() + " | ID: " + c.getId());
                }
            }
        } finally {
            tenantContext.clear();
        }
    }
}