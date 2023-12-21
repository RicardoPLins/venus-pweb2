package br.edu.ifpb.pweb2.venus.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatriculaValidator.class)
@Documented
public @interface Matricula {

    String message() default "Matrícula inválida, deve ter 7 dígitos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
