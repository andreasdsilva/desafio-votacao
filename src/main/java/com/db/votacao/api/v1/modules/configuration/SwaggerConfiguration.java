package com.db.votacao.api.v1.modules.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.ExternalDocumentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiDoc() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Aplicação para controlar um sistema de votação")
                        .contact(new Contact()
                                .name("Andreas D. Silva").email("andreas.silva@universo.univates.br"))
                        .description("Aplicação desenvolvida para desafio técnico")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório GitHub")
                        .url("https://github.com/andreasdsilva/desafio-votacao"));
    }
}
