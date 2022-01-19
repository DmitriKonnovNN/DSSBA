package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.interceptors.TestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //registry.addInterceptor(new TestInterceptor()).addPathPatterns("/**");
    }

    //TODO: addFormatters (FormatterRegistry registry)
}
