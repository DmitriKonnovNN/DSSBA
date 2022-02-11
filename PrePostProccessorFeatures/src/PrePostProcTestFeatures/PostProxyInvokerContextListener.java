package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;


import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.SneakyThrows;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;


import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class PostProxyInvokerContextListener /*implements ApplicationListener<ContextRefreshedEvent> */{


    /**
     * The factory is suppose to give us an access to original beans. Its usage stems from getting proxies instead while
     * accessing context in the for-loop below. */

    @Autowired
    private ConfigurableListableBeanFactory factory;


    @SneakyThrows
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {

        ApplicationContext context = event.getApplicationContext();
        String [] names = context.getBeanDefinitionNames();

        for (String name: names) {
            try {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            if (originalClassName == null){continue;}
            /*try {*/
               Class<?>originalClass = ClassUtils.resolveClassName(originalClassName, ClassLoader.getSystemClassLoader());
                Method [] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        System.out.println("PostProxy's running");
                        Object bean = context.getBean(name);
                        method.invoke(bean);
                    }
                }

           /* } catch (Exception e) {e.printStackTrace();}*/

        } catch (NoSuchBeanDefinitionException e){e.getStackTrace();}
    }


}}
