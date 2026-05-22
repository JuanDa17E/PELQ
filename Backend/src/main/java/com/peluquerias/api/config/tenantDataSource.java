package com.peluquerias.api.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class tenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantContext.getDbUrl();
    }
}