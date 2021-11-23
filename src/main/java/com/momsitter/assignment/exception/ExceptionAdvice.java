package com.momsitter.assignment.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MomSitterException.class)
    public ResponseEntity<ExceptionResponse> KitchenposExceptionHandler(MomSitterException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentValidException(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(extractErrorMessages(e).get(0));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> constraintViolationException(final ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(extractErrorMessages(e).get(0));
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
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getMessage()));
    }
}
