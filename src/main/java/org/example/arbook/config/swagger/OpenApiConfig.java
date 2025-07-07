package org.example.arbook.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "AR BOOK", version = "v1", description = "AR BOOK PROJECT"),
        servers = @Server(url = "http://13.60.252.171"),
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
)
public class OpenApiConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String SCHEME_NAME = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SCHEME_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ));
    }


//
//    @Bean
//    public OpenApiCustomizer dynamicFilter(CategoryRepository categoryRepository) {
//        return openApi -> {
//            List<Category> categories = categoryRepository.findAll();
//            List<String> categoryNames = categories.stream().map(item -> item.getName()).toList();
//            openApi.getPaths().forEach((path, pathltem) -> {
//                pathltem.readOperations().forEach(operation -> {
//                    List<Parameter> parameters = operation.getParameters();
//                    if (parameters != null) {
//                        parameters.forEach(parameter -> {
//                            if ("categoryName".equals(parameter.getName())) {
//                                Schema<String> schema = parameter.getSchema();
//                                if (schema != null) {
//                                    schema.setEnum(categoryNames);
//                                }
//                            }
//                        });
//                    }
//                });
//            });
//        };
//    }

}
