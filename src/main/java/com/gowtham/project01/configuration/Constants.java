package com.gowtham.project01.configuration;

import java.util.List;

public class Constants {
    private Constants() {
    }

    public static final List<String> PUBLIC_URLS = List.of(
            "/api/v1/signup",
            "/api/v1/login",
            "/health",
            "/api/v1/authorizer",
            "/v3/api-docs",
            "/swagger-ui");

}
