package com.capstone.ai_painter_backen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.capstone.ai_painter_backen.domain.message")
public class AiPainterBackenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiPainterBackenApplication.class, args);
    }
}
