package br.widsl.rinhabackend.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;
import br.widsl.rinhabackend.exception.impl.BadRequestException;
import br.widsl.rinhabackend.exception.impl.DatabaseException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.mapper.PersonMapper;
import br.widsl.rinhabackend.repository.PersonRepository;
import br.widsl.rinhabackend.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Cacheable("persons-cache")
    private Optional<PersonEntity> findBySurname(String surname) {
        return personRepository.findBySurname(surname);
    }

    @Override
    @Cacheable("persons-cache")
    public PersonDTO findById(String id) {

        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Constants.INVALID_ID);
        }

        Optional<PersonEntity> entity = personRepository.findById(uuid);

        return entity.map(PersonMapper::parseDTO)
                .orElseThrow(() -> new PersonNotFound(Constants.EMPTY_PERSON.formatted(id)));

    }

    @Override
    @Cacheable("persons-cache")
    public List<PersonDTO> findByTerm(String term) {

        List<PersonEntity> entity;

        if (term == null || term.isEmpty())
            return new ArrayList<>();

        if (term.matches("\\d{4}-\\d{2}-\\d{2}"))
            entity = personRepository.findByDate(LocalDate.parse(term));
        else
            entity = personRepository.findByTerm(term);

        return entity.stream()
                .map(PersonMapper::parseDTO)
                .toList();

    }

    @Override
    public PersonDTO savePerson(PersonDTO personDTO) {

        try {

            findBySurname(personDTO.getSurname()).ifPresent(x -> {
                throw new BadRequestException(Constants.EXISTENT_PERSON.formatted(x.getSurname()));
            });

            PersonEntity saved = personRepository.save(PersonMapper.parseEntity(personDTO));
            personDTO.setId(saved.getId());
            return personDTO;

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException(Constants.SAVE_ERROR.formatted(personDTO.getSurname()));
        }

    }

    @Override
    public PersonCountDTO personCount() {
        List<PersonEntity> result = personRepository.findAll();
        return new PersonCountDTO(result.size());
    }

}
