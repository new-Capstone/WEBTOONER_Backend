package com.capstone.ai_painter_backen.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ai_painter_public")//일반 사용자 그룹 설정
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("ai_painter-admin")//관리자 사용자 그룹
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("AI PAINTER API")
                        .description("AI Painter's api docs!!")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://aipainter.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("ai_painter's  Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }



}
