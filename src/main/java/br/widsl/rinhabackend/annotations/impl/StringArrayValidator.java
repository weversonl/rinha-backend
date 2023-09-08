package br.widsl.rinhabackend.annotations.impl;

import br.widsl.rinhabackend.annotations.StringArray;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringArrayValidator implements ConstraintValidator<StringArray, String[]> {
    private int maxLength;

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
