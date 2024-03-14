package br.widsl.rinhabackend.annotations.impl;

import org.springframework.stereotype.Component;

import br.widsl.rinhabackend.annotations.StringArray;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class StringArrayValidator implements ConstraintValidator<StringArray, String[]> {

    private int maxLength;

    public StringArrayValidator() {
        /* default constructor */ }

    @Override
    public void initialize(StringArray constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String[] values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }

        for (String value : values) {
            if (value == null || value.isEmpty() || value.length() > maxLength) {
                return false;
            }
        }

        return true;
    }
}
