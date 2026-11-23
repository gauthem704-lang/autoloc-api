package fr.ece.autoloc.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityDemoController {

    @GetMapping("/public/ping")
    public String publicPing() {
        return "Public endpoint is reachable";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user";
    }

    @GetMapping("/admin/report")
    public String adminReport() {
        return "Confidential admin report";
    }
}
