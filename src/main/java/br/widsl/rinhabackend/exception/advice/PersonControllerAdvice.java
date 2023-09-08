package br.widsl.rinhabackend.exception.advice;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.DatabaseException;
import br.widsl.rinhabackend.exception.impl.ExistentPersonException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.exception.model.ApiErrorResponse;
import br.widsl.rinhabackend.exception.model.ErrorValidation;

@RestControllerAdvice
public class PersonControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFound.class)
    public ResponseEntity<ApiErrorResponse> handlePersonNotFound(PersonNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(Constants.NOT_FOUND_EX)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ExistentPersonException.class)
    public ResponseEntity<ApiErrorResponse> handleExistentPersonException(ExistentPersonException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(Constants.BAD_REQUEST_DESC)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabaseException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(Constants.INTERNAL_SERVER_EX)
                        .description(Constants.INTERNAL_SERVER_DESC)
                        .build());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(Constants.BAD_REQUEST_EX)
                .description(Constants.BAD_REQUEST_DESC)
                .build();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String description = Objects.requireNonNull(error.getDefaultMessage()).formatted(error.getField());
            apiErrorResponse.addError(new ErrorValidation(description));
        });

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiErrorResponse);
    }

}
