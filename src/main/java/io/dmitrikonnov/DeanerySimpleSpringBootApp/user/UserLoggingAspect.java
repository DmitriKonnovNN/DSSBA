package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.BadRequestParameterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.checkerframework.checker.nullness.Opt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Optional;

@Component
@Aspect
@Slf4j
public class UserLoggingAspect {


    private final static String TOKEN_EXPIRATION_TIME_RESET_MSG = "Confirmation Token's Expiration time has been " +
            "reset to its default value: %s";
    private final static String TOKEN_EXPIRATION_TIME_SET_MSG = "Confirmation Token's Expiration time has been " +
            "set to value: %s";



    @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.user.*)")
    private void inUserPackagePoint () {}

    /**
     *  Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserController*)")
     * */
    @Pointcut ("within(io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserController*)")
    protected void inUserControllerPoint (){}

    @Pointcut ("execution(* set*(..))")
    private void anySetPoint() {}

    @Pointcut ("execution(* resetConfirmationTokenExpirationTime(..))")
    private void resetConfTokenExpTimePoint(){}

    /**
     * Pointcut ("execution(* setConfirmationTokenExpirationTime(..))")
     * */
    @Pointcut ("execution(* setConfirmationTokenExpirationTime(..))")
    protected void setConfTokenExpTimePoint(){}


    @AfterReturning (value = "inUserControllerPoint() && resetConfTokenExpTimePoint()", returning = "retVal")
    public void afterResetConfirmationTokenExpTime(int retVal){
        log.warn(String.format(TOKEN_EXPIRATION_TIME_RESET_MSG,retVal));
    }

    @Before("inUserControllerPoint() &&  setConfTokenExpTimePoint() && args(time)")
    public void beforeSetConfirmationTokenExpTime(Object time) {
        if (time instanceof Optional<?>){
            var value = (Optional<?>)time;
           var value1 = value.get();
           if (value1 instanceof Integer){
               System.out.println("CHECK DONE: " + value.get());
           }

        }


    }




    @AfterReturning("inUserControllerPoint() &&  setConfTokenExpTimePoint() && args(time)")
    public void afterSetConfirmationTokenExpTime(/*JoinPoint joinPoint,*/ Optional<Integer> time){
        log.info(String.format(TOKEN_EXPIRATION_TIME_SET_MSG, time.get()));
       /* if (joinPoint.getArgs()[0] instanceof Optional<?>){
            var value = (Optional<?>) joinPoint.getArgs()[0];
            log.info(String.format(TOKEN_EXPIRATION_TIME_SET_MSG, value.get()));
        }*/
    }

  /*  @AfterReturning ("inUserControllerPoint() && anySetPoint() && !setConfTokenExpTimePoint() ")
    public void afterAnyOtherSet(){

    }
*/
}
