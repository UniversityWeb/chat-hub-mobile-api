package com.universityweb.chathubmobileapi.document;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "vanannek",
                        email = "info.vananne@gmail.com",
                        url = "@{server.owner.url}"
                ),
                description = "OpenAPI documentation for the Chat Hub",
                title = "Chat Hub API - vanannek",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8000"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "${server.prod.url}"
                )
        }
)
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("API Version 1")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}
