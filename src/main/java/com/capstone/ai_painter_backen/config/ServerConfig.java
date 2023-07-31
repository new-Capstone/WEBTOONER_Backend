package com.capstone.ai_painter_backen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Value("${modelServer.pixarUrl")
    private String pixarUrl;

    @Value("${modelServer.loraUrl")
    private String loraUrl;
}
