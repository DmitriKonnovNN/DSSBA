package io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation which indicates that web request body should have a JSON Structure
 * appropriate to DTO-Class to be given in in parameters. This will be mapped to the Domain Level Object by DTOModelMapper
 * in Runtime, which extends {@link package.org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor }.
 * */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestDTO {

   Class value();
}
