package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

@Component
public class InjectRandomIntAnnotationPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field [] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInteger annotation = field.getAnnotation(InjectRandomInteger.class);
            if (annotation!=null) {
                int min = annotation.min();
                int max = annotation.max();
                Random random = new Random();
                int timesToRepeat = min + random.nextInt(max - min);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, timesToRepeat);
            }
        }
        if (bean instanceof Quoter) {
           ((Quoter) bean).say();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
