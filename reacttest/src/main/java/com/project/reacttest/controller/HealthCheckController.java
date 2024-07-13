package com.project.reacttest.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthCheckController {
    @GetMapping("/")
    public String healthCheck(){
        return "The service is up and running...";
    }
}
