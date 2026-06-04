package com.superrrr.franky.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI frankyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franky API")
                        .version("1.0.0")
                        .description("API de gestión de productos, sucursales y ventas")
                        .contact(new Contact()
                                .name("Franky Support")
                                .email("support@franky.com")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer JWT"))
                .components(new Components()
                        .addSecuritySchemes("Bearer JWT", new SecurityScheme()
                                .name("Bearer JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
