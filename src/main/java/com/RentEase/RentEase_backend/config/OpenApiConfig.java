package com.RentEase.RentEase_backend.config;

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
                title = "RentEase API",
                description = "Comprehensive API documentation for RentEase, a platform facilitating property rental and management. Explore endpoints for user management, property listings, tenancy agreements, and more.",
                termsOfService = "https://rentease.com/terms",
                contact = @Contact(
                        name = "Amrit Raj",
                        email = "amrit.raj1504@gmail.com"
                ),
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "Development Environment",
                        url = "http://localhost:8084"
                ),
                @Server(
                        description = "Testing Environment (Under Maintenance)",
                        url = "http://localhost:8083"
                )
        },
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer",
        description = "Enter your JWT token in the format: Bearer {your_token}"
)
public class OpenApiConfig {
}
