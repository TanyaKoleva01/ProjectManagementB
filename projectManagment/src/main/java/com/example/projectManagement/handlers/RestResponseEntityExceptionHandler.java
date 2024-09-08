package com.example.projectManagement.handlers;

import com.example.projectManagement.dtos.ErrorsDto;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorsDto> constraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorsDto(ex.getConstraintViolations()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RepositoryConstraintViolationException.class)
    protected ResponseEntity<ErrorsDto> repositoryConstraintViolationException(RepositoryConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorsDto(ex.getErrors()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> validationException(ValidationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
