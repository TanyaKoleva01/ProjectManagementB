package com.example.projectManagement.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ErrorsDto implements Serializable {

    private Set<FieldErrorDto> errors = new LinkedHashSet<>();

    public ErrorsDto(Errors errors) {
        for (FieldError error : errors.getFieldErrors()) {
            this.errors.add(new FieldErrorDto(
                    error.getField(),
                    error.getCode(),
                    error.getDefaultMessage()));
        }
    }

    public ErrorsDto(Set<ConstraintViolation<?>> constraintViolations) {
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            errors.add(new FieldErrorDto(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getConstraintDescriptor()
                            .getAnnotation().annotationType().getSimpleName(),
                    constraintViolation.getMessage()));
        }
    }
}
