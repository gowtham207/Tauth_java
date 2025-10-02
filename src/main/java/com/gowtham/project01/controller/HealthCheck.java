package com.gowtham.project01.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/health")
public class HealthCheck {
    @GetMapping
    public String HealthCheckApi(@RequestParam(required = false, defaultValue = "World") String param) {
        return "Health Check API " + param;
    }

}
