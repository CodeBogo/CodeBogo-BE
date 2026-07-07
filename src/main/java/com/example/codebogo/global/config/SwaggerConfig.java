package com.example.codebogo.global.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("https://codebogo.kang0131.shop")
                                .description("배포 서버")
                ))
                .info(new Info()
                        .title("CodeBogo API")
                        .description("CodeBogo 해커톤용 퀴즈 백엔드 API 문서")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("CodeBogo Team")
                                .email("team@codebogo.local"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Springdoc Reference")
                        .url("http://springdoc.org/"));
    }
}