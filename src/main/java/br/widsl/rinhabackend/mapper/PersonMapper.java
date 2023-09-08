package br.widsl.rinhabackend.mapper;

import java.time.LocalDate;

import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;

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

    public static PersonEntity parseEntity(PersonDTO dto) {
        PersonEntity entity = new PersonEntity();
        if (dto != null) {
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setBirth(LocalDate.parse(dto.getBirth()));
            entity.setStack(dto.getStack());
        }
        return entity;
    }

}
