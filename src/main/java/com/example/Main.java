package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("🚀 Blockchain Voting API running on http://localhost:8080");
        System.out.println("📊 Test endpoints:");
        System.out.println("  GET  http://localhost:8080/api/voting/chain");
        System.out.println("  GET  http://localhost:8080/api/voting/valid");
        System.out.println("  GET  http://localhost:8080/api/voting/results");
        System.out.println("  POST http://localhost:8080/api/voting/vote");
    }
}