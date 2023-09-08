package br.widsl.rinhabackend.annotations.impl;

import br.widsl.rinhabackend.annotations.StringArray;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringArrayValidatorTest {
    private StringArrayValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        StringArray stringArray = Mockito.mock(StringArray.class);
        Mockito.when(stringArray.maxLength()).thenReturn(10);

        validator = new StringArrayValidator();
        validator.initialize(stringArray);

        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void testIsValidWhenArrayIsNullThenReturnTrue() {

        String[] values = null;

        boolean result = validator.isValid(values, context);

        assertTrue(result);
    }

    @Test
    void testIsValidWhenArrayContainsNullThenReturnFalse() {

        String[] values = { null };

        boolean result = validator.isValid(values, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenArrayContainsEmptyStringThenReturnFalse() {

        String[] values = { "" };

        boolean result = validator.isValid(values, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenArrayContainsLongStringThenReturnFalse() {

        String[] values = { "This is a very long string that exceeds the max length" };

        boolean result = validator.isValid(values, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenArrayContainsValidStringsThenReturnTrue() {

        String[] values = { "Valid", "Strings" };

        boolean result = validator.isValid(values, context);

        assertTrue(result);
    }
}