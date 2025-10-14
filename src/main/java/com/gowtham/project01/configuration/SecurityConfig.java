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
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/js/**", "/css/**", "/images/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        // allow login & signup without authentication
                        .requestMatchers("/api/v1/signup", "/api/v1/login", "/health", "/api/v1/authorizer").permitAll()
                        // everything else requires authentication
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
