package com.doublev.ticketservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ticket Service API",
                version = "v1",
                description = "API para gestión de Usuarios y Tickets (prueba técnica Double V Partners).",
                contact = @Contact(
                        name = "Miguel Angel Claros",
                        email = "tu_correo@ejemplo.com"
                ),
                license = @License(
                        name = "Uso educativo / prueba técnica"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor local",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
