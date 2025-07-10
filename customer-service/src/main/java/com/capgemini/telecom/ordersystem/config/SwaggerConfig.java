package com.capgemini.telecom.ordersystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI()
                .info(
                        new Info().title("Customer APIs")
                                .description("Action learning project")
                )
                .servers(Collections.singletonList(new Server().url("http://localhost:8081").description("local")))
                .tags(Collections.singletonList(
                                new Tag().name("Customer APIs")
                        )
                );
    }
}