package com.thewayhome.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("TheWayHome API")
                .version(springdocVersion)
                .description("TheWayHome 서비스를 위한 API 서버입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
