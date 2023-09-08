package br.widsl.rinhabackend.annotations.impl;

import java.time.LocalDate;

import br.widsl.rinhabackend.annotations.BirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<BirthDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (validateStringData(value) && validateStringPattern(value)) {
            LocalDate date = LocalDate.parse(value);
            return !date.isAfter(LocalDate.now());
        }

        return false;

    }

    private boolean validateStringData(String date) {
        return date != null && !date.isEmpty();
    }

    private boolean validateStringPattern(String date) {
        return date.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][\\d]|3[01])$");
    }

}
