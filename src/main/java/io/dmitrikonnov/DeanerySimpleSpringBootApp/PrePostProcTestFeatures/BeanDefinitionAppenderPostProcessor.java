package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanDefinitionAppenderPostProcessor implements BeanPostProcessor {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(factory.getBeanDefinitionNames()).parallel().forEach(name ->{
            if(neededPostProxyBehaviour(bean)) {
                BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
                if (beanDefinition.getBeanClassName()==null){
                    beanDefinition.setBeanClassName(bean.getClass().getCanonicalName());
                }}
        });
        return bean;
    }

    private boolean neededPostProxyBehaviour (Object bean) {
        return Arrays.stream(bean.getClass().getMethods()).anyMatch(method -> method.isAnnotationPresent(PostProxy.class));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
