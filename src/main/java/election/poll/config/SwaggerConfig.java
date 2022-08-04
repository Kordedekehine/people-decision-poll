package election.poll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket pollApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("election.poll"))
               // .paths(regex("/product.*"))
                .build()
                .apiInfo(apiDetails());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "PEOPLE POLLING APP API",
                "Set of Apis for PEOPLE POLLING APP",
                "1.0",
                "For PEOPLE POLLING APP only",
                new Contact("Salami Kehinde Korede","PEOPLE POLLING APP","PEOPLE POLLING APP"),
                "PEOPLE POLLING APP License",
                "PEOPLE POLLING APP",
                Collections.emptyList()
        );
    }
    //link address http://localhost:8080/v2/api-docs
    //final link address http://localhost:8080/swagger-ui.html#/
}
