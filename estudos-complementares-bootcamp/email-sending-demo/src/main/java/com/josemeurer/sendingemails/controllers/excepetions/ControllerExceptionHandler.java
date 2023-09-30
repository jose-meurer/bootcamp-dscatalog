package com.josemeurer.sendingemails.controllers.excepetions;

import com.josemeurer.sendingemails.services.exceptions.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandardError> email(EmailException e, HttpServletRequest http) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Email Error");
        error.setMessage(e.getMessage());
        error.setPath(http.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
