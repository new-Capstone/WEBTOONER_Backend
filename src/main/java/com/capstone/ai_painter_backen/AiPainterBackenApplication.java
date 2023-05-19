package com.capstone.ai_painter_backen;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(servers = {@Server(url= "https://capstone-webtooner.com") })//todo 점검
public class AiPainterBackenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiPainterBackenApplication.class, args);
    }
}
