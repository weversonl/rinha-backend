package br.widsl.rinhabackend.annotations.impl;

import br.widsl.rinhabackend.annotations.NonNullFields;
import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonNullFieldsValidator implements ConstraintValidator<NonNullFields, String> {

    private String fieldName;

    @Override
    public void initialize(NonNullFields annotation) {
        fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            throw new UnprocessableEntityException(Constants.FIELD_REQUIRED.formatted(fieldName));
        }
        return true;
    }

}
