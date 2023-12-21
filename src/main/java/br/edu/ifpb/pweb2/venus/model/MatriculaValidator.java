package br.edu.ifpb.pweb2.venus.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatriculaValidator implements ConstraintValidator<Matricula, String> {

    @Override
    public void initialize(Matricula constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Verifique se o valor é diferente de nulo e tem exatamente 7 dígitos
        return value != null && value.matches("\\d{7}");
    }
}
