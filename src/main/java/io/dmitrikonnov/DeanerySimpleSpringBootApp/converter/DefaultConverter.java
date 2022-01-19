package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter;

public interface DefaultConverter {
    default String getConverterName () {
        return "No default converter implemented nor used";
    }
}
