package com.upiiz.EquiposDeportivos.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Equipos Deportivos",
                version = "1.0.0",
                description = "API para la gestión de equipos deportivos",
                contact = @Contact(
                        name = "Jesus Miguel Hernandez Garcia",
                        email = "jhernandezg1710@alumno.ipn.mx",
                        url = "http://localhost:8080/api/v1"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "http://localhost:8080/api/v1"
        ),
        servers = {
                @Server(
                description = "Servidor local de pruebas",
                url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de producción",
                        url = "https://wcbdf-jmhg-ejercicio-08.onrender.com"
                )
        }
)
public class OpenApiConfiguration {
}
