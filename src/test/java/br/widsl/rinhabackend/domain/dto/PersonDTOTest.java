package br.widsl.rinhabackend.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class PersonDTOTest {

    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personDTO = new PersonDTO();
    }

    @Test
    void testToStringWhenAllPropertiesSetThenReturnsCorrectFormat() {

        UUID id = UUID.randomUUID();
        String surname = "Doe";
        String name = "John";
        String birth = "2000-01-01";
        String[] stack = { "Java", "Spring" };
        personDTO.setId(id);
        personDTO.setSurname(surname);
        personDTO.setName(name);
        personDTO.setBirth(birth);
        personDTO.setStack(stack);

        String actual = personDTO.toString();

        String expected = "PersonDTO{id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", stack=" + Arrays.toString(stack) +
                '}';
        assertEquals(expected, actual);
    }

    @Test
    void testToStringWhenNoPropertiesSetThenReturnsCorrectFormat() {

        String actual = personDTO.toString();

        String expected = "PersonDTO{id=null, surname='null', name='null', birth='null', stack=null}";
        assertEquals(expected, actual);
    }

    @Test
    void testToStringReturnsCorrectFormat() {

        UUID id = UUID.randomUUID();
        String surname = "Doe";
        String name = "John";
        String birth = "2000-01-01";
        String[] stack = { "Java", "Spring" };
        personDTO.setId(id);
        personDTO.setSurname(surname);
        personDTO.setName(name);
        personDTO.setBirth(birth);
        personDTO.setStack(stack);

        String actual = personDTO.toString();

        String expected = "PersonDTO{id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", stack=" + Arrays.toString(stack) +
                '}';
        assertEquals(expected, actual);
    }
}