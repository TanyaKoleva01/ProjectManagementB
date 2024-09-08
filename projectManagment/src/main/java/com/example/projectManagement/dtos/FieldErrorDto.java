package com.example.projectManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class FieldErrorDto implements Serializable {

    /**
     * name of field in input json that has an error.
     */
    private String field;

    /**
     * code used to i18n to identify the error.
     */
    private String code;

    /**
     * english message for api developers.
     */
    @EqualsAndHashCode.Exclude
    private String message;
}
