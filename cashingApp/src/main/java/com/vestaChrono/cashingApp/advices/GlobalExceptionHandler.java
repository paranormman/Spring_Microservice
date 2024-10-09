package com.vestaChrono.cashingApp.advices;

import com.vestaChrono.cashingApp.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error(exception.getLocalizedMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<?> handleStaleObjectStateException(StaleObjectStateException exception) {
        log.error(exception.getLocalizedMessage());
        return new ResponseEntity<>("Stale data \n", HttpStatus.CONFLICT);
    }

}
