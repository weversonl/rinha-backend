package br.widsl.rinhabackend.annotations.impl;

import br.widsl.rinhabackend.annotations.BirthDate;
import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;

import static br.widsl.rinhabackend.constants.Constants.DATE_PATTERN;

public class DateValidator implements ConstraintValidator<BirthDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (validateStringData(value) && validateStringPattern(value)) {
            try {
                LocalDate date = LocalDate.parse(value);
                return !date.isAfter(LocalDate.now());
            } catch (DateTimeException e) {
                throw new UnprocessableEntityException(Constants.INVALID_DATE);
            }

        }

        return false;

    }

    private boolean validateStringData(String date) {
        return date != null && !date.isEmpty();
    }

    private boolean validateStringPattern(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

}
