package br.widsl.rinhabackend.exception.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.BadRequestException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.exception.model.ApiErrorResponse;

@ExtendWith(MockitoExtension.class)
class PersonControllerAdviceTest {

        @Mock
        private MethodArgumentNotValidException methodArgumentNotValidException;

        @Mock
        private BindingResult bindingResult;

        private PersonControllerAdvice personControllerAdvice;

        @BeforeEach
        void setUp() {
                personControllerAdvice = new PersonControllerAdvice();
        }

        @Test
        void testHandleMethodArgumentNotValidWhenSingleFieldErrorThenReturnsExpectedResponse() {

                String exceptionMessage = "Invalid argument";
                FieldError fieldError = new FieldError("objectName", "field", exceptionMessage);
                when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
                when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handleMethodArgumentNotValid(methodArgumentNotValidException);

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody())).getCode());
                assertEquals(Constants.BAD_REQUEST_EX, Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(Constants.BAD_REQUEST_DESC,
                                Objects.requireNonNull(responseEntity.getBody()).getDescription());
                assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).getErrors().size());
                assertEquals(exceptionMessage,
                                Objects.requireNonNull(responseEntity.getBody()).getErrors().get(0).getDescription());
        }

        @Test
        void testHandleMethodArgumentNotValidWhenMultipleFieldErrorsThenReturnsExpectedResponse() {

                String exceptionMessage1 = "Invalid argument 1";
                String exceptionMessage2 = "Invalid argument 2";
                FieldError fieldError1 = new FieldError("objectName", "field1", exceptionMessage1);
                FieldError fieldError2 = new FieldError("objectName", "field2", exceptionMessage2);
                when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
                when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handleMethodArgumentNotValid(methodArgumentNotValidException);

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                Objects.requireNonNull(responseEntity.getBody()).getCode());
                assertEquals(Constants.BAD_REQUEST_EX, Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(Constants.BAD_REQUEST_DESC,
                                Objects.requireNonNull(responseEntity.getBody()).getDescription());
                assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).getErrors().size());
                assertEquals(exceptionMessage1,
                                Objects.requireNonNull(responseEntity.getBody()).getErrors().get(0).getDescription());
                assertEquals(exceptionMessage2,
                                Objects.requireNonNull(responseEntity.getBody()).getErrors().get(1).getDescription());
        }

        @Test
        void testHandleMethodArgumentNotValidWhenMethodArgumentNotValidExceptionThrownThenReturnsCorrectHttpStatusAndErrorMessage() {

                String exceptionMessage = "Invalid argument";
                FieldError fieldError = new FieldError("objectName", "field", exceptionMessage);
                when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
                when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handleMethodArgumentNotValid(methodArgumentNotValidException);

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                Objects.requireNonNull(responseEntity.getBody()).getCode());
                assertEquals(Constants.BAD_REQUEST_EX, Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(Constants.BAD_REQUEST_DESC,
                                Objects.requireNonNull(responseEntity.getBody()).getDescription());
                assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).getErrors().size());
                assertEquals(exceptionMessage,
                                Objects.requireNonNull(responseEntity.getBody()).getErrors().get(0).getDescription());
        }

        @Test
        void testHandleDatabaseExceptionWhenDatabaseExceptionThrownThenReturnsCorrectResponseEntity() {

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handleDatabaseException();

                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                Objects.requireNonNull(responseEntity.getBody()).getCode());
                assertEquals(Constants.INTERNAL_SERVER_EX,
                                Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(Constants.INTERNAL_SERVER_DESC,
                                Objects.requireNonNull(responseEntity.getBody()).getDescription());
        }

        @Test
        void testHandlePersonNotFoundWhenPersonNotFoundExceptionThenReturnsCorrectResponseEntity() {

                String exceptionMessage = "Person not found";

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handlePersonNotFound(new PersonNotFound(exceptionMessage));

                assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.NOT_FOUND.value(), Objects.requireNonNull(responseEntity.getBody()).getCode());
                assertEquals(Constants.NOT_FOUND_EX, Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(exceptionMessage, Objects.requireNonNull(responseEntity.getBody()).getDescription());
        }

        @Test
        void testHandleExistentPersonExceptionWhenInvokedThenReturnsCorrectResponseEntity() {

                String exceptionMessage = "Person already exists";

                ResponseEntity<ApiErrorResponse> responseEntity = personControllerAdvice
                                .handleBadRequestException(new BadRequestException(exceptionMessage));

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
                assertNotNull(Objects.requireNonNull(responseEntity.getBody()));
                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                Objects.requireNonNull(responseEntity.getBody()).getCode());
                assertEquals(Constants.BAD_REQUEST_DESC, Objects.requireNonNull(responseEntity.getBody()).getMessage());
                assertEquals(exceptionMessage, Objects.requireNonNull(responseEntity.getBody()).getDescription());
        }
}