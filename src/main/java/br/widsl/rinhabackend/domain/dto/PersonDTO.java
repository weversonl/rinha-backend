package br.widsl.rinhabackend.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.redis.core.RedisHash;

import br.widsl.rinhabackend.annotations.BirthDate;
import br.widsl.rinhabackend.annotations.StringArray;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.constraints.NotBlank;

@RedisHash(value = "person", timeToLive = Constants.TTL_REDIS)
public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6583635153965709585L;

    private UUID id;

    @NotBlank(message = Constants.FIELD_REQUIRED)
    @Length(max = 32, message = Constants.FIELD_SIZE)
    private String surname;

    @NotBlank(message = Constants.FIELD_REQUIRED)
    @Length(max = 100, message = Constants.FIELD_SIZE)
    private String name;

    @BirthDate
    private String birth;

    @StringArray
    private String[] stack;

    public PersonDTO(String surname, String name, String birth, String[] stack) {
        this.surname = surname;
        this.name = name;
        this.birth = birth;
        this.stack = stack;
    }

    public PersonDTO() {

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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String[] getStack() {
        return stack;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }

}
