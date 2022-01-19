package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.RequestDTO;
import lombok.extern.log4j.Log4j;

import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;

/**
 * Legitly copied from:
 *
 * https://auth0.com/blog/automatically-mapping-dto-to-entity-on-spring-boot-apis/
 */



public class DTOModelMapper extends RequestResponseBodyMethodProcessor implements DefaultConverter {


    private static final ModelMapper modelMapper = new ModelMapper();




    private EntityManager entityManager;

    public DTOModelMapper (ObjectMapper objectMapper, EntityManager entityManager) {
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
        this.entityManager = entityManager;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestDTO.class);
    }

   

    /**
     * We overwrite resolveArgument. This is the most important method in our implementation.
     * We tweak it to embed the ModelMapper instance in the process and make it map DTOs into entities.
     * But before mapping, we check if we are handling a new entity, or if we have to apply the changes
     * proposed by the DTO in an existing entity.
*/


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {



        Object dto = super.resolveArgument( parameter, mavContainer, webRequest, binderFactory);

        Object id = getEntityId(dto);
        if (id == null){
            return modelMapper.map(dto, parameter.getParameterType());
        } else {
            Object persistedObject = entityManager.find(parameter.getParameterType(), id);
            modelMapper.map(dto, persistedObject);
            return persistedObject;
        }
    }

    /**
     * We overwrite the readWithMessageConverters method. The base class simply takes the parameter type
     * and converts the request into an instance of it. We overwrite this method to make the conversion
     * to the type defined in the DTO annotation, and leave the mapping from the DTO to the entity
     * to the resolveArgument method.
     * */

    @Override
    protected Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        for (Annotation annot : parameter.getParameterAnnotations()) {
            RequestDTO dtoType = AnnotationUtils.getAnnotation(annot, RequestDTO.class);
            if (dtoType != null ) {
                return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
            }
            
        }
        throw new RuntimeException();
    }

    /**
     * We define a getEntityId method. This method iterates over the fields of the DTO being populate to check
     * if there is one marked with @Id. If it finds, it returns the value of the field so resolveArgument can
     * query the database with it.
     */

    private Object getEntityId (@NotNull Object dto) {
        for (Field field : dto.getClass().getDeclaredFields()) {
            if(field.getAnnotation(Id.class) != null) {
                try {field.setAccessible(true);
                return field.get(dto);}
                catch (IllegalAccessException e) {
                    throw new RuntimeException();
                }
            }
        }
        return null;
    }
    @Override
    public String getConverterName(){
        return "DTO Model Mapper that extends RequestResponseBodyMethodProcessor  ";
    }



}
