package br.widsl.rinhabackend.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "persons")
public class PersonEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private String surname;
    private String name;
    private LocalDate birth;
    private String[] stack;

    public PersonEntity(UUID id, String surname, String name, LocalDate birth, String[] stack) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.birth = birth;
        this.stack = stack;
    }

    public PersonEntity() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String[] getStack() {
        return stack;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
