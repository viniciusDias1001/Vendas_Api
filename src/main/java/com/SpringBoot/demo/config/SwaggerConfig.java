package com.SpringBoot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
         return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.basePackage("com.SpringBoot.demo.rest.controller")).
                 paths(PathSelectors.any()).build().securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).apiInfo(apiInfo());


    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Vendas_Com_SpringBoot").contact(contact()).description("Essa é uma API feita com visão no estudo e no aprendizado. ").version("1.0").build();
    }

    private Contact contact(){
        return new Contact("Pedro Vinicius Barros","https://github.com/viniciusDias1001","pedrorochadias1001@gmail.com");
    }


    public ApiKey apiKey(){
        return new ApiKey("JWT","Authorization","header");
    }

    private SecurityContext securityContext(){
        return  SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    public List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope= new AuthorizationScope("global","accessEverything");

        AuthorizationScope[] scopes= new AuthorizationScope[1];
        scopes[0] = authorizationScope;

        SecurityReference reference = new SecurityReference("JWT",scopes);
        List<SecurityReference> list = new ArrayList<>();
        list.add(reference);
        return list;


    }
}
