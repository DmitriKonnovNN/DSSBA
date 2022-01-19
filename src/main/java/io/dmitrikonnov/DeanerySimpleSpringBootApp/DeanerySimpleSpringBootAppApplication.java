package io.dmitrikonnov.DeanerySimpleSpringBootApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;


@SpringBootApplication
@Import(SpringDataRestConfiguration.class)
public class DeanerySimpleSpringBootAppApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {

		SpringApplication.run(DeanerySimpleSpringBootAppApplication.class, args);

	}

}
