package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInteger {
    int min();
    int max();
}
