package br.widsl.rinhabackend.exception.impl;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

}
