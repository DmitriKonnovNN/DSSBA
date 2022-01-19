package io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Weist darauf hin, dass es sich beim Class um ein Data Transfer Object handelt.
 */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DTO {

    }
