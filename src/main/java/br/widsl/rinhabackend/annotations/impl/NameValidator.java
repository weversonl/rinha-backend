package br.widsl.rinhabackend.annotations.impl;

import static br.widsl.rinhabackend.constants.Constants.NAME_VERIFY_PATTERN;

import br.widsl.rinhabackend.annotations.NameValidation;
import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.BadRequestException;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new UnprocessableEntityException(Constants.FIELD_REQUIRED.formatted("name"));
        } else if (!NAME_VERIFY_PATTERN.matcher(value).matches()) {
            throw new BadRequestException(Constants.NAME_ERROR_VALIDATION);
        }
        return true;
    }

}
