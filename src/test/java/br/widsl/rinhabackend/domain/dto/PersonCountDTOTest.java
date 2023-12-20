package br.widsl.rinhabackend.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonCountDTOTest {

    @Test
    void testGetPersonsWhenNoValueSetThenReturnsNull() {
        PersonCountDTO personCountDTO = new PersonCountDTO();
        Integer result = personCountDTO.getPersons();
        assertThat(result).isNull();
    }

    @Test
    void testGetPersonsWhenValueSetThenReturnsValue() {
        PersonCountDTO personCountDTO = new PersonCountDTO();
        Integer expectedValue = 5;
        personCountDTO.setPersons(expectedValue);
        Integer result = personCountDTO.getPersons();
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void testGetPersonsWhenConstructedWithValueThenReturnsValue() {
        Integer expectedValue = 5;
        PersonCountDTO personCountDTO = new PersonCountDTO(expectedValue);
        Integer result = personCountDTO.getPersons();
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void testSetPersonsWhenCalledThenFieldIsSet() {
        PersonCountDTO personCountDTO = new PersonCountDTO();
        Integer expectedValue = 5;
        personCountDTO.setPersons(expectedValue);
        Integer result = personCountDTO.getPersons();
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void testSetPersonsWhenCalledWithNullThenFieldIsNull() {
        PersonCountDTO personCountDTO = new PersonCountDTO();
        personCountDTO.setPersons(null);
        Integer result = personCountDTO.getPersons();
        assertThat(result).isNull();
    }
}