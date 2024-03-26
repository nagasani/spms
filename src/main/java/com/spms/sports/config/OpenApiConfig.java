package com.spms.sports.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public org.springdoc.core.models.GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("sports-public")
                .packagesToScan("com.spms.sports.controller")
                .build();
    }

    @Bean
    @Primary
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Player API")
                .description("API for managing players and their associated sports")
                .version("v1.0.0"));
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sports API")
                        .description("API for managing sports and their associations")
                        .version("v1"));
    }
}
