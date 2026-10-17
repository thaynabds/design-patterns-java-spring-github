package br.com.thaynabdstec.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "status", "ok",
                "servico", "customer-service",
                "mensagem", "API de design patterns no ar. O perfil portfolio usa H2 em memoria."
        );
    }
}
