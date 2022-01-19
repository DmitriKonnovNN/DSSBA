package io.dmitrikonnov.DeanerySimpleSpringBootApp.exception;

public class UniqueConstraintException extends RuntimeException {

    public UniqueConstraintException(String message) {
        super(message);
    }
}
