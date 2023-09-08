package br.widsl.rinhabackend.domain.dto;

import java.io.Serial;
import java.io.Serializable;

public class PersonCountDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer persons;

    public PersonCountDTO(Integer persons) {
        this.persons = persons;
    }

    public PersonCountDTO() {

    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

}
