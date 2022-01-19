package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingAnnotationPostProcessor implements BeanPostProcessor {

    private Map<String, Class > annotatedBeans = new HashMap<>();
    private CustomProfilingController customProfilingController = new CustomProfilingController();

    public ProfilingAnnotationPostProcessor() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(customProfilingController, new ObjectName("customProfiling","name","controller" ));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            annotatedBeans.put(beanName,beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = annotatedBeans.get(beanName);
        if (beanClass!=null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader()
                    , beanClass.getInterfaces()
                    , (proxy, method, args) -> {
                        if (customProfilingController.isEnable()) {System.out.println("PROFILING RUNNING");
                            long before = System.nanoTime();
                            Object retVal = method.invoke(bean, args);
                            long after = System.nanoTime();
                            System.out.println(after - before);
                            System.out.println("Profiling stopped");
                            return retVal;}
                        else return method.invoke(bean, args);

                    });
        }
        return bean;
    }
}
