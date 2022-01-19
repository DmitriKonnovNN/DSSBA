package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.CustomConverter;

import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
@AllArgsConstructor
public class  CustomConverterConfiguration {


    private final Set<Converter<?,?>> converters;
    private final ConfigurableConversionService conversionService;

    @EventListener (ContextRefreshedEvent.class)
    public void onApplicationEvent(){
        converters.forEach(conversionService::addConverter);
    }


}
