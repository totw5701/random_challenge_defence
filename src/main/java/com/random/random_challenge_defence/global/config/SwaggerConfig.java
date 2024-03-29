package com.random.random_challenge_defence.global.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.random.random_challenge_defence.domain"))
                .paths(Predicates.or(
                        PathSelectors.ant("/challenge-log/**"),
                        PathSelectors.ant("/challenge-card/**"),
                        PathSelectors.ant("/file/**"),
                        PathSelectors.ant("/recommend/**"),
                        PathSelectors.ant("/member-personality/**"),
                        PathSelectors.ant("/members/**"),
                        PathSelectors.ant("/auth/**"),
                        PathSelectors.ant("/challenge-card-feedback/**")
                        )
                )
                //.paths(PathSelectors.ant("/challenge-log/**"))
                //.paths(PathSelectors.ant("/file/**"))
                .build();
    }

}