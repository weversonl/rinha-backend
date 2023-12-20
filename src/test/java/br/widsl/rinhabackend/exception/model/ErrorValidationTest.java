package br.widsl.rinhabackend.exception.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ErrorValidationTest {

    private ErrorValidation errorValidation;

    @BeforeEach
    void setUp() {
        errorValidation = new ErrorValidation();
    }

    @Test
    void testGetDescriptionWhenDescriptionIsSetThenReturnsCorrectValue() {

        String expectedDescription = "Test description";
        errorValidation.setDescription(expectedDescription);

        String actualDescription = errorValidation.getDescription();

        assertEquals(expectedDescription, actualDescription, "getDescription did not return the correct value");
    }

    @Test
    void testGetDescriptionWhenDescriptionIsNotSetThenReturnsNull() {

        String actualDescription = errorValidation.getDescription();

        assertNull(actualDescription, "getDescription did not return null when description is not set");
    }

    @Test
    void testSetDescriptionWhenValidStringThenDescriptionIsSet() {

        String expectedDescription = "Test description";

        errorValidation.setDescription(expectedDescription);
        String actualDescription = errorValidation.getDescription();

        assertEquals(expectedDescription, actualDescription, "setDescription did not set the correct value");
    }

    @Test
    void testSetDescriptionWhenNullThenDescriptionIsNull() {

        errorValidation.setDescription(null);
        String actualDescription = errorValidation.getDescription();

        assertNull(actualDescription, "setDescription did not handle null input correctly");
    }
}