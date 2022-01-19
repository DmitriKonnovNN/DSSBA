package io.dmitrikonnov.DeanerySimpleSpringBootApp.exception;

public class FileEmptyException extends RuntimeException{
    public FileEmptyException(String msg) { super(msg); }
}
