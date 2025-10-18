package com.gowtham.project01.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvLoader implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .ignoreIfMissing()
                .load();

        Map<String, Object> props = new HashMap<>();

        if (dotenv.get("DB_URL") != null) {
            props.put("spring.datasource.url", dotenv.get("DB_URL"));
        }
        if (dotenv.get("DB_USERNAME") != null) {
            props.put("spring.datasource.username", dotenv.get("DB_USERNAME"));
        }
        if (dotenv.get("DB_PASSWORD") != null) {
            props.put("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        }
        // DB_SCHEMA
        if (dotenv.get("DB_SCHEMA") != null) {
            props.put("spring.jpa.properties.hibernate.default_schema", dotenv.get("DB_SCHEMA"));
        }

        environment.getPropertySources().addFirst(new MapPropertySource("dotenv", props));
    }
}
