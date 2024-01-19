package br.widsl.rinhabackend.mapper;

import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

public class PersonMapper {

    private PersonMapper() {

    }

    public static PersonDTO parseDTO(PersonEntity entity) {
        PersonDTO personDTO = new PersonDTO();
        if (entity != null) {
            personDTO.setId(entity.getId());
            personDTO.setName(entity.getName());
            personDTO.setSurname(entity.getSurname());
            personDTO.setBirth(entity.getBirth().toString());
            personDTO.setStack(entity.getStack());
        }
        return personDTO;
    }

    public static @NonNull PersonEntity parseEntity(PersonDTO dto) {
        PersonEntity entity = new PersonEntity();
        if (dto != null) {
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setBirth(LocalDate.parse(dto.getBirth()));
            entity.setStack(dto.getStack());
        }
        return entity;
    }

}
