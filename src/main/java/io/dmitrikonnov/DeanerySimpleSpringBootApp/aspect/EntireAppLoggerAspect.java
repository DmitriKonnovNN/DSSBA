package io.dmitrikonnov.DeanerySimpleSpringBootApp.aspect;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.PersonDto;
import static io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.LoggingMessage.*;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
@Slf4j
public class EntireAppLoggerAspect {
    private final static String ANY_METHOD_CALL_MSG = "Method %s is being called";
    private final static String AFTER_ANY_SUCCESSFUL_EXECUTION_MSG = "Method %s has been successfully executed";
    private final static String AFTER_ANY_RUNTIME_EX_MSG = "Runtime Exception thrown: %s ";
    private final static String AFTER_ANY_METHOD_IN_CONTROLLER_CALL_MSG = "A method in controller %s has been called";

    @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.exceptionhandler.ExceptionHandlerService*)")
    private void inExceptionHandlerServicePoint (){

    }

    @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp..*)")
    private void inAnyPackagePoint(){
    }

    @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp..*Controller)")
    private void inAnyControllerPoint() {
    }

   @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures.*)")
    private void inPrePostProcessorFeaturesPoint(){}


    @Pointcut ("execution(* *(..))")
    private void anyExecutionPoint () {}

    @Pointcut ("execution(* get*(..))")
    private void executionAnyGetPoint () {}

    @Pointcut ("execution (* getAll (..))")
    private void executionGetAllPoint (){}


    @Pointcut ("execution(* add* (..))")
    private void executionAnyAddPoint(){}



    @AfterThrowing (value = "inAnyPackagePoint() && !inPrePostProcessorFeaturesPoint()", throwing = "ex")
    public void afterThrowingAnyRuntimeException(RuntimeException ex){
        log.warn(String.format(AFTER_ANY_RUNTIME_EX_MSG,ex.getMessage()));


    }


    @After("anyExecutionPoint() && inAnyPackagePoint() &&!inExceptionHandlerServicePoint ()  && !inPrePostProcessorFeaturesPoint()")
    public void afterAnyCall (JoinPoint joinPoint) {
        log.trace(String.format(ANY_METHOD_CALL_MSG,joinPoint.getSignature().getName()));

    }
    @AfterReturning ("anyExecutionPoint() && inAnyPackagePoint()  && !inPrePostProcessorFeaturesPoint()")
    public void setAfterAnySuccessfulExecutionMsg(JoinPoint joinPoint){
        log.trace(String.format(AFTER_ANY_SUCCESSFUL_EXECUTION_MSG,joinPoint.getSignature().getName()));
    }

    @After("anyExecutionPoint() && inAnyControllerPoint()")
    public void controllerExecutionPoint(JoinPoint joinPoint){
        log.trace(String.format(AFTER_ANY_METHOD_IN_CONTROLLER_CALL_MSG, joinPoint.getTarget().getClass().getName()));
    }

    @AfterReturning("executionAnyGetPoint() && inAnyPackagePoint()  && !inPrePostProcessorFeaturesPoint()")
    public void afterGetPersonInController (JoinPoint joinPoint) {

        log.trace("get-Method " + joinPoint.getSignature().getName() + " has been executed");
    }

    @AfterReturning("executionGetAllPoint()")
    public void afterGetAllExecution (JoinPoint joinPoint) {
        log.info("GetAll-method was called in class: " + joinPoint.getTarget().toString());
    }

    @AfterReturning ("executionAnyAddPoint() && inAnyPackagePoint() && args(personDto)  && !inPrePostProcessorFeaturesPoint()")
    public void afterAddPersonInController (JoinPoint joinPoint, PersonDto personDto) {

        log.info("In CONTROLLER: " + joinPoint.getTarget().toString() + " . Arguments passed: " + personDto);
    }

    @AfterReturning ("inAnyPackagePoint()&& executionAnyGetPoint()  && !inPrePostProcessorFeaturesPoint()")
    public void afterGreetingExecution (JoinPoint joinPoint) {

        log.trace("Arguments passed to a GET-Method: " + Arrays.toString(joinPoint.getArgs()));
    }
 /* See:
 * https://wkrzywiec.medium.com/moving-into-next-level-in-user-log-events-with-spring-aop-3b4435892f16
 * */

}
