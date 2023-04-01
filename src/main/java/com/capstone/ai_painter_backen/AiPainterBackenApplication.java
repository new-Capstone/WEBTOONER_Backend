package com.capstone.ai_painter_backen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AiPainterBackenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiPainterBackenApplication.class, args);
    }
}
