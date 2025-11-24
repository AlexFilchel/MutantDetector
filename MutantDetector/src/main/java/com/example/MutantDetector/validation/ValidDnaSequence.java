package com.example.MutantDetector.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {
    String message() default "Secuencia de ADN inválida: debe ser una matriz NxN (mínimo 4x4) con caracteres A, T, C o G.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

