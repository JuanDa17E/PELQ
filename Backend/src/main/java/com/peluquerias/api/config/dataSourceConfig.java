package com.peluquerias.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class dataSourceConfig {

    @Value("${spring.datasource.url}")
    private String adminUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        tenantDataSource routingDataSource = new tenantDataSource();

        DriverManagerDataSource adminDataSource = new DriverManagerDataSource();
        adminDataSource.setUrl(adminUrl);
        adminDataSource.setUsername(username);
        adminDataSource.setPassword(password);
        adminDataSource.setDriverClassName("org.postgresql.Driver");

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("admin", adminDataSource);

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(adminDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}