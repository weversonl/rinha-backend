package br.widsl.rinhabackend.exception.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ApiErrorResponseBuilder Test")
class ApiErrorResponseBuilderTest {

    @Test
    @DisplayName("Should set the errors correctly in the ApiErrorResponseBuilder")
    void setRealizedInApiErrorResponseBuilder() {
        List<ErrorValidation> errors = new ArrayList<>();
        ApiErrorResponse.ApiErrorResponseBuilder apiErrorResponseBuilder = new ApiErrorResponse.ApiErrorResponseBuilder();

        ApiErrorResponse.ApiErrorResponseBuilder result = apiErrorResponseBuilder.errors(errors);

        assertEquals(errors, result.build().getErrors());
    }

    @Test
    @DisplayName("Should set the description correctly in the ApiErrorResponseBuilder")
    void setPriorityInApiErrorResponseBuilder() {
        String description = "description";
        ApiErrorResponse.ApiErrorResponseBuilder apiErrorResponseBuilder = new ApiErrorResponse.ApiErrorResponseBuilder();

        ApiErrorResponse.ApiErrorResponseBuilder result = apiErrorResponseBuilder.description(description);

        assertEquals(description, result.build().getDescription());
    }

    @Test
    @DisplayName("Should set the message correctly in the builder")
    void setDescriptionInBuilder() {
        String message = "message";
        ApiErrorResponse.ApiErrorResponseBuilder apiErrorResponseBuilder = new ApiErrorResponse.ApiErrorResponseBuilder();

        ApiErrorResponse.ApiErrorResponseBuilder result = apiErrorResponseBuilder.message(message);

        assertEquals(message, result.build().getMessage());
    }

    @Test
    @DisplayName("Should set the code correctly in the builder")
    void setNameInBuilder() {
        Integer code = 500;
        ApiErrorResponse.ApiErrorResponseBuilder apiErrorResponseBuilder = new ApiErrorResponse.ApiErrorResponseBuilder();

        ApiErrorResponse.ApiErrorResponseBuilder result = apiErrorResponseBuilder.code(code);

        assertEquals(code, result.build().getCode());
    }

}
