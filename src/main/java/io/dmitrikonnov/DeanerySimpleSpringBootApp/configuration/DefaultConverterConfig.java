package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.DTOModelMapper;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.DefaultConverter;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.OrikaConverter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.persistence.EntityManager;

@Configuration
@AllArgsConstructor (onConstructor_ ={@Autowired} )
public class DefaultConverterConfig {

    private final ApplicationContext applicationContext;
    private final EntityManager entityManager;
    @Value ("${defaultConverter.converter}") private final String qualifier;
    final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String CONVERTER_CONFIG_MSG = "Default DTO to Entity Configuration : %s";
    private final String ORIKA_CONVERTER = "orikaConverter";
    private final String MODEL_MAPPER_CONVERTER = "modelMapperConverter";


    @Bean ("defaultConverter")
    public RequestResponseBodyMethodProcessor getDefaultConverter (){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
        if (qualifier.equals(ORIKA_CONVERTER)) {
            log.warn(String.format(CONVERTER_CONFIG_MSG,ORIKA_CONVERTER));
            return new OrikaConverter(objectMapper,entityManager);
        }
        if (qualifier.equals(MODEL_MAPPER_CONVERTER)) {
            log.warn(String.format(CONVERTER_CONFIG_MSG,MODEL_MAPPER_CONVERTER));
            return new DTOModelMapper(objectMapper,entityManager);
        }
        else throw new RuntimeException("No default converter defined in the properties");

    }
}
