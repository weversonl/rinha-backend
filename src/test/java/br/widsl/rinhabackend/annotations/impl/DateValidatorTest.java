package br.widsl.rinhabackend.annotations.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
class DateValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    void testIsValidWhenValidDateStringThenTrue() {

        DateValidator dateValidator = new DateValidator();
        String validDateString = "2000-01-01";

        boolean result = dateValidator.isValid(validDateString, context);

        assertTrue(result);
    }

    @Test
    void testIsValidWhenInvalidDateStringThenFalse() {

        DateValidator dateValidator = new DateValidator();
        String invalidDateString = "2000/01/01";

        boolean result = dateValidator.isValid(invalidDateString, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenNullStringThenFalse() {

        DateValidator dateValidator = new DateValidator();
        String nullString = null;

        boolean result = dateValidator.isValid(nullString, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenEmptyStringThenFalse() {

        DateValidator dateValidator = new DateValidator();
        String emptyString = "";

        boolean result = dateValidator.isValid(emptyString, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenFutureDateStringThenFalse() {

        DateValidator dateValidator = new DateValidator();
        String futureDateString = "3000-01-01";

        boolean result = dateValidator.isValid(futureDateString, context);

        assertFalse(result);
    }

    @Test
    void testIsValidWhenInputIsNullThenReturnFalse() {

        DateValidator dateValidator = new DateValidator();

        String input = null;

        boolean result = dateValidator.isValid(input, context);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsValidWhenInputIsEmptyThenReturnFalse() {

        DateValidator dateValidator = new DateValidator();

        String input = "";

        boolean result = dateValidator.isValid(input, context);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsValidWhenInputIsInvalidDateFormatThenReturnFalse() {

        DateValidator dateValidator = new DateValidator();

        String input = "2021/12/31";

        boolean result = dateValidator.isValid(input, context);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsValidWhenInputIsFutureDateThenReturnFalse() {

        DateValidator dateValidator = new DateValidator();

        String input = "3000-12-31";

        boolean result = dateValidator.isValid(input, context);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsValidWhenInputIsValidDateThenReturnTrue() {

        DateValidator dateValidator = new DateValidator();

        String input = "2000-12-31";

        boolean result = dateValidator.isValid(input, context);

        Assertions.assertTrue(result);
    }
}