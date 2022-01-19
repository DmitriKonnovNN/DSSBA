package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.CustomConverter;

import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface CustomConversionService extends ConversionService {

    default <T,S> List <T> convert (List<S> sources, Class<T> targetClass ){
        return Objects.isNull(sources) ? Collections.emptyList():  sources.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }
}
