package br.widsl.rinhabackend.exception.advice;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.exception.impl.*;
import br.widsl.rinhabackend.exception.model.ApiErrorResponse;
import br.widsl.rinhabackend.exception.model.ErrorValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class PersonControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(PersonControllerAdvice.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFound.class)
    public ResponseEntity<ApiErrorResponse> handlePersonNotFound(PersonNotFound exception) {
        log.warn("PersonNotFoundEx -> {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(Constants.NOT_FOUND_EX)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException exception) {
        log.warn("BadRequestEx -> {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(Constants.BAD_REQUEST_DESC)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiErrorResponse> handleUnprocessableEntityException(UnprocessableEntityException exception) {
        log.warn("UnprocessableEntityEx -> {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(Constants.DATABASE_EX)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabaseException(DatabaseException exception) {
        log.warn("DatabaseEx -> {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(Constants.DATABASE_EX)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ApiErrorResponse> handleTechnicalException(TechnicalException exception) {
        log.warn("TechnicalEx -> {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(Constants.DATABASE_EX)
                        .description(exception.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Constants.BAD_REQUEST_EX)
                .description(Constants.BAD_REQUEST_DESC)
                .build();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            log.warn("MethodArgumentNotValidEx -> {}", Objects.requireNonNull(error.getDefaultMessage()).formatted(error.getField()));
            String description = Objects.requireNonNull(error.getDefaultMessage()).formatted(error.getField());
            apiErrorResponse.addError(new ErrorValidation(description));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {

        log.warn("MissingServletRequestParameterException -> {}", exception.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Constants.BAD_REQUEST_EX)
                .description(Constants.BAD_REQUEST_DESC)
                .build();

        String message = Constants.FIELD_REQUIRED.formatted(exception.getParameterName());

        apiErrorResponse.addError(new ErrorValidation(message));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

}
