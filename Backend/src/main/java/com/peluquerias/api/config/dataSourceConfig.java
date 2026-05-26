package com.peluquerias.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class dataSourceConfig {

    @Value("${spring.datasource.url}")
    private String adminUrl;

    @Value("${spring.datasource.username}")
    private String adminUser;

    @Value("${spring.datasource.password}")
    private String adminPassword;

    private tenantDataSource routingDataSource;
    private final Map<Object, Object> targetDataSources = new HashMap<>();

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource adminDs = crearPool("admin", adminUrl, adminUser, adminPassword);
        targetDataSources.put("admin", adminDs);

        routingDataSource = new tenantDataSource();
        routingDataSource.setDefaultTargetDataSource(adminDs);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    public void registrarTenant(String tenantId, String url, String user, String password) {
        if (targetDataSources.containsKey(tenantId)) return;

        HikariDataSource ds = crearPool(tenantId, url, user, password);
        targetDataSources.put(tenantId, ds);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();
    }

    private HikariDataSource crearPool(String poolName, String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url + "?prepareThreshold=0");
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");
        config.setPoolName("pool-" + poolName);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }
}