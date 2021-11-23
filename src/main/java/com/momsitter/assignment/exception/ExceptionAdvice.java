package com.momsitter.assignment.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(MomSitterException.class)
    public ResponseEntity<ExceptionResponse> KitchenposExceptionHandler(MomSitterException e) {
        LOGGER.warn(e.getMessage());

        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentValidException(final MethodArgumentNotValidException e) {
        ExceptionResponse exceptionResponse = extractErrorMessages(e).get(0);
        LOGGER.warn(exceptionResponse.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> constraintViolationException(final ConstraintViolationException e) {
        ExceptionResponse exceptionResponse = extractErrorMessages(e).get(0);
        LOGGER.warn(exceptionResponse.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    private List<ExceptionResponse> extractErrorMessages(final MethodArgumentNotValidException e) {
        return e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .map(ExceptionResponse::new)
            .collect(Collectors.toList());
    }

    private List<ExceptionResponse> extractErrorMessages(final ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .map(ExceptionResponse::new)
            .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        LOGGER.error(e.getMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getMessage()));
    }
}
