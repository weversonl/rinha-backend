package br.widsl.rinhabackend.annotations.impl;

import br.widsl.rinhabackend.annotations.NonNullFields;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NonNullFieldsValidatorTest {

    private NonNullFieldsValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private NonNullFields annotation;

    @BeforeEach
    void setUp() {
        validator = new NonNullFieldsValidator();
        validator.initialize(annotation);
    }

    @Test
    void testIsValidWhenInputIsNullThenThrowsUnprocessableEntityException() {
        String input = null;
        assertThrows(UnprocessableEntityException.class, () -> validator.isValid(input, context));
    }

    @Test
    void testIsValidWhenInputIsEmptyStringThenThrowsUnprocessableEntityException() {
        String input = "";
        assertThrows(UnprocessableEntityException.class, () -> validator.isValid(input, context));
    }

    @Test
    void testIsValidWhenInputIsNonEmptyStringThenReturnsTrue() {
        String input = "non-empty string";
        boolean result = validator.isValid(input, context);
        assertTrue(result);
    }
}