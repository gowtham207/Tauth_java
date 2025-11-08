package com.gowtham.project01.configuration;

import java.util.List;

public class Constants {
    private Constants() {
    }

    public static final List<String> PUBLIC_URLS = List.of(
            "/ui",
            "/js",
            "/css",
            "/health",
            "/auth",
            "/api/v1/auth/",
            "/api/v1/auth/",
            "/api/v1/authorizer",
            "/v3/api-docs",
            "/swagger-ui");
}
