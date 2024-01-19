package br.widsl.rinhabackend.annotations.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.widsl.rinhabackend.exception.impl.BadRequestException;
import jakarta.validation.ConstraintValidatorContext;

class NameValidatorTest {

    private final NameValidator nameValidator = new NameValidator();
    private final ConstraintValidatorContext context = null;

    @Test
    void testIsValidWhenNameIsValidThenReturnTrue() {
        String validName = "John Doe";
        boolean result = nameValidator.isValid(validName, context);
        assertTrue(result, "Expected isValid to return true for a valid name");
    }

    @Test
    void testIsValidWhenNameIsInvalidThenThrowBadRequestException() {
        String invalidName = "John123";
        assertThrows(BadRequestException.class, () -> nameValidator.isValid(invalidName, context),
                "Expected isValid to throw BadRequestException for an invalid name");
    }
}