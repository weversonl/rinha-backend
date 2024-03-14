package br.widsl.rinhabackend.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.widsl.rinhabackend.annotations.StringArray;
import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.deserializers.StringArrayDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

@RedisHash(value = "Person", timeToLive = Constants.TTL_REDIS)
public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotBlank
    @Length(max = 32)
    @JsonProperty("apelido")
    private String surname;

    @NotBlank
    @Length(max = 100)
    @Pattern(regexp = "^[\\p{L}\\s]+$")
    @JsonProperty("nome")
    private String name;

    @NotNull
    @PastOrPresent
    @JsonProperty("nascimento")
    private LocalDate birth;

    @StringArray
    @JsonProperty("stack")
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private String[] stack;

    public PersonDTO(String surname, String name, LocalDate birth, String[] stack) {
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String[] getStack() {
        if (this.stack == null) {
            stack = new String[] {};
        }
        return stack;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }

}
