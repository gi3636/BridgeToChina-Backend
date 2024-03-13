package com.btchina.common.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private Info info() {
        return new Info()
                .title("API接口文档")
                .description("")
                .version("v1.0.0");
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        ExternalDocumentation externalDocumentation = new ExternalDocumentation()
                .description("留华桥接口文档")
                .url("http://localhost:8080/swagger-ui.html");
        return new OpenAPI()
                .info(info())
                .externalDocs(externalDocumentation);
    }

}
