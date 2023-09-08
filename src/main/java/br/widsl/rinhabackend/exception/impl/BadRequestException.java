package br.widsl.rinhabackend.exception.impl;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
