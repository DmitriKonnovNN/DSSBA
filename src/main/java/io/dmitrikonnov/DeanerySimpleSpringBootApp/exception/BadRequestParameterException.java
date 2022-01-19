package io.dmitrikonnov.DeanerySimpleSpringBootApp.exception;

public class BadRequestParameterException extends RuntimeException{

    public BadRequestParameterException (String msg) {super(msg);}
}
