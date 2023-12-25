package br.widsl.rinhabackend.domain.dto;

import br.widsl.rinhabackend.annotations.BirthDate;
import br.widsl.rinhabackend.annotations.NameValidation;
import br.widsl.rinhabackend.annotations.NonNullFields;
import br.widsl.rinhabackend.annotations.StringArray;
import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.deserializers.StringArrayDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@RedisHash(value = "Person", timeToLive = Constants.TTL_REDIS)
public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6583635153965709585L;

    private UUID id;

    @NonNullFields(fieldName = "apelido")
    @Length(max = 32, message = Constants.FIELD_SIZE)
    @JsonProperty("apelido")
    private String surname;

    @Length(max = 100, message = Constants.FIELD_SIZE)
    @NameValidation
    @JsonProperty("nome")
    private String name;

    @BirthDate
    @NonNullFields(fieldName = "nascimento")
    @JsonProperty("nascimento")
    private String birth;

    @StringArray
    @JsonProperty("stack")
    @JsonDeserialize(using = StringArrayDeserializer.class)
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

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", stack=" + Arrays.toString(stack) +
                '}';
    }

}
