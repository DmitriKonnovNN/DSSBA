package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration;


import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;

@EnableAsync
@EnableSwagger2
@EnableAspectJAutoProxy
@Configuration
@EnableConfigurationProperties
@EnableScheduling
@EnableTransactionManagement
@PropertySource("classpath:appproperties.properties")

public class ConfigurationProperties {





    @Bean
    public Docket swaggerConfiguration () {
        // Returns a prepared Docket instance.
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("io.dmitrikonnov.DeanerySimpleSpringBootApp"))
                .build()
                .apiInfo(getApiDetails());
    }

    private ApiInfo getApiDetails () {
        return new ApiInfo(
                "Deanery Simple Spring Boot Application",
                "No further description",
                "1.3 Test",
                "free to use",
                new Contact("Dmitri Konnov", "http://dmitrikonnov.io","lildkonnov.nnov@inbox.ru"),
                "API license",
                "http://dmitrikonnov.io",
                Collections.emptyList());
    }
}
