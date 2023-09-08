package br.widsl.rinhabackend.exception.impl;

public class PersonNotFound extends RuntimeException {
    public PersonNotFound(String message) {
        super(message);
    }
}
