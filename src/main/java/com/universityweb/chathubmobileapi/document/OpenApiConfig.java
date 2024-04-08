package com.universityweb.chathubmobileapi.document;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

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
public class OpenApiConfig {
}
