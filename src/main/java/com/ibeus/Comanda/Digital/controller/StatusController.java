package com.ibeus.Comanda.Digital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public String status() {
        return "API Comanda Digital rodando 🚀";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}