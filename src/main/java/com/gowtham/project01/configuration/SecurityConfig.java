package com.gowtham.project01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        // allow swagger
                        .requestMatchers(Constants.PUBLIC_URLS.toArray(new String[0]))
                        .permitAll()
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/ui/*",
                                "/js/*",
                                "/js/cdn/*",
                                "/css/*",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/swagger-ui/swagger-ui.css",
                                "/swagger-ui/swagger-ui-bundle.js",
                                "/swagger-ui/swagger-ui-standalone-preset.js",
                                "/swagger-ui/swagger-initializer.js")
                        .permitAll()
                        // everything else requires authentication
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                String allowedOrigins = System.getProperty("CORS_ALLOWED_ORIGINS", "*");
                String allowedMethods = System.getProperty("CORS_ALLOWED_METHODS", "GET, POST, PUT, DELETE, OPTIONS");
                String allowedHeaders = System.getProperty("CORS_ALLOWED_HEADERS", "*");
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods(allowedMethods.split(", "))
                        .allowedHeaders(allowedHeaders.split(", "));
            }
        };
    }

}
