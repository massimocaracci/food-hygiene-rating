package uk.co.pantasoft.fhr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

@Profile({"!test", "!prod"})
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket accidentAPI() {

        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(Instant.class, Date.class)
                .directModelSubstitute(OffsetDateTime.class, Date.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("uk.co.pantasoft"))
                .build()
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("Pantasoft API - V1")
                .description("REST API Specification")
                .version("1.0.0")
                .build();
    }
}