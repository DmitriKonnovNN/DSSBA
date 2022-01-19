package io.dmitrikonnov.DeanerySimpleSpringBootApp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PersistenceLoggerAspect {

    Logger log = LoggerFactory.getLogger(this.getClass());


    @Pointcut("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService.*)")
    private void inServicePackagePoint (){}


    @Pointcut ("execution(* add*(..))")
    private void savePoint () {}



    @After("savePoint() && inServicePackagePoint()")
    public void afterSave (JoinPoint joinPoint) {

        log.info("log1: " +joinPoint.getTarget().toString());
        log.info("log2: "+joinPoint.getSignature().getName());
}

}
