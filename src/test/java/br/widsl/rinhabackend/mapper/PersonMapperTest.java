package br.widsl.rinhabackend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;

class PersonMapperTest {

    @Test
    void testParseDTOWhenEntityIsNotNullThenReturnsCorrectDTO() {

        UUID id = UUID.randomUUID();
        String surname = "Doe";
        String name = "John";
        LocalDate birth = LocalDate.now();
        String[] stack = { "Java", "Spring" };
        PersonEntity entity = new PersonEntity(surname, name, birth, stack);
        entity.setId(id);

        PersonDTO dto = PersonMapper.parseDTO(entity);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(surname, dto.getSurname());
        assertEquals(name, dto.getName());
        assertEquals(birth, LocalDate.parse(dto.getBirth()));
        assertEquals(stack, dto.getStack());

    }

    @Test
    void testParseDTOWhenEntityIsNullThenReturnsEmptyDTO() {

        PersonEntity entity = null;

        PersonDTO dto = PersonMapper.parseDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getSurname());
        assertNull(dto.getName());
        assertNull(dto.getBirth());
        assertNull(dto.getStack());
    }

    @Test
    void testParseEntityWhenPersonDTOProvidedThenReturnsCorrectPersonEntity() {

        String surname = "Doe";
        String name = "John";
        String birth = LocalDate.now().toString();
        String[] stack = { "Java", "Spring" };
        PersonDTO dto = new PersonDTO(surname, name, birth, stack);

        PersonEntity entity = PersonMapper.parseEntity(dto);

        assertNotNull(entity);
        assertEquals(surname, entity.getSurname());
        assertEquals(name, entity.getName());
        assertEquals(LocalDate.parse(birth), entity.getBirth());
        assertEquals(stack, entity.getStack());

    }

    @Test
    void testParseEntityWhenNullPersonDTOProvidedThenReturnsPersonEntityWithNullFields() {

        PersonDTO dto = null;

        PersonEntity entity = PersonMapper.parseEntity(dto);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getSurname());
        assertNull(entity.getName());
        assertNull(entity.getBirth());
        assertNull(entity.getStack());
    }
}