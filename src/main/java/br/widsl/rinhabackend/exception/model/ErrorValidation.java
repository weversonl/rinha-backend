package br.widsl.rinhabackend.exception.model;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorValidation implements Serializable {

    @Serial
    private static final long serialVersionUID = -2275400324162925033L;

    private String description;

    public ErrorValidation(String description) {
        this.description = description;
    }

    public ErrorValidation() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
