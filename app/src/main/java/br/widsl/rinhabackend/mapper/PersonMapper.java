package br.widsl.rinhabackend.mapper;

import org.springframework.lang.NonNull;

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
            personDTO.setBirth(entity.getBirth());
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
            entity.setBirth(dto.getBirth());
            entity.setStack(dto.getStack());
        }
        return entity;
    }

}
