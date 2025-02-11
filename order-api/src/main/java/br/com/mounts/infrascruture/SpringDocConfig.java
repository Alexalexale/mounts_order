package br.com.mounts.infrascruture;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Order API")
                .description(
                    "Serviço responsavel por expor os endpoints para consultas de pedidos.")
                .version("v0.0.1"))
        .externalDocs(
            new ExternalDocumentation()
                .description("GitHub Page")
                .url("https://github.com/Alexalexale/mounts_order"));
  }
}
