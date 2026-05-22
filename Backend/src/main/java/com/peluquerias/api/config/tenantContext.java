package com.peluquerias.api.config;

public class tenantContext {

    private static final ThreadLocal<String> dbUrl = new ThreadLocal<>();

    public static void setDbUrl(String url) {
        dbUrl.set(url);
    }

    public static String getDbUrl() {
        return dbUrl.get();
    }

    public static void clear() {
        dbUrl.remove();
    }
}