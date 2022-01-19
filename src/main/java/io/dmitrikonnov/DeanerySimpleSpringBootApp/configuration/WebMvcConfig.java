package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.DTOModelMapper;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.DefaultConverter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.persistence.EntityManager;
import java.util.List;

@Configuration
@EnableWebMvc
@AllArgsConstructor (onConstructor_ = {@Autowired})
public class WebMvcConfig implements WebMvcConfigurer {



    @Qualifier("defaultConverter") private final RequestResponseBodyMethodProcessor defaultConverter;


        @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/greeting").setViewName("greeting");
    }

/*    @Override
    public void configureViewResolvers (ViewResolverRegistry registry) {
      registry.freeMarker("/resources/templates/",);
    }*/
/*
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".ftlh");
        return resolver;
    }

*/





    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //objectMapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        resolvers.add(defaultConverter);

        }
}
