package br.widsl.rinhabackend.exception.impl;

public class ExistentPersonException extends RuntimeException {
    public ExistentPersonException(String message) {
        super(message);
    }
}
