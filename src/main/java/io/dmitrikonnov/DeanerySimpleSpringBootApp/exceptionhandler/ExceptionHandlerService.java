package io.dmitrikonnov.DeanerySimpleSpringBootApp.exceptionhandler;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.BadRequestParameterException;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.FileEmptyException;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.NotFoundException;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.UniqueConstraintException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;

@RestController
@ControllerAdvice
public class ExceptionHandlerService {
    public static Logger log = LoggerFactory.getLogger(ExceptionHandlerService.class);

    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<String> notFound (NotFoundException ex){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @ExceptionHandler (NoSuchElementException.class)
    public ResponseEntity<String> throwNoSuchElementException (NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler ({UniqueConstraintException.class})
    public ResponseEntity<String> uniqueConstraintViolated (UniqueConstraintException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler (FileEmptyException.class)
    public ResponseEntity<String> fileIsEmpty (FileEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<String> noSuchObject (NoSuchObjectException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestParameterException.class)
    public ResponseEntity<String>noMatchingParameters (BadRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }



}
