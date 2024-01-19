package br.widsl.rinhabackend.service.impl;

import static br.widsl.rinhabackend.constants.Constants.DATE_PATTERN;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;
import br.widsl.rinhabackend.exception.impl.BadRequestException;
import br.widsl.rinhabackend.exception.impl.DatabaseException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import br.widsl.rinhabackend.mapper.PersonMapper;
import br.widsl.rinhabackend.repository.PersonRepository;
import br.widsl.rinhabackend.service.PersonService;

@Service
@CacheConfig(cacheNames = "persons-cache")
public class PersonServiceImpl implements PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Cacheable("#surname")
    private boolean findBySurname(String surname) {
        return personRepository.findBySurname(surname).isPresent();
    }

    @Override
    @Cacheable("#id")
    public PersonDTO findById(String id) {

        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid ID for -> %s".formatted(id));
            throw new BadRequestException(Constants.INVALID_ID);
        }

        Optional<PersonEntity> entity = personRepository.findById(requireNonNull(uuid));

        return entity.map(PersonMapper::parseDTO)
                .orElseThrow(() -> new PersonNotFound(Constants.EMPTY_PERSON.formatted(id)));

    }

    @Override
    public List<PersonDTO> findByTerm(String term) {

        List<PersonEntity> entity;

        if (term == null || term.isEmpty())
            return new ArrayList<>();

        if (DATE_PATTERN.matcher(term).matches())
            entity = personRepository.findByDate(LocalDate.parse(term));
        else
            entity = personRepository.findByTerm(term);

        return entity.stream()
                .map(PersonMapper::parseDTO)
                .toList();

    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<PersonDTO> savePerson(PersonDTO personDTO) {

        try {

            if (findBySurname(personDTO.getSurname())) {
                log.error("Existent person for surname -> %s".formatted(personDTO.getSurname()));
                throw new UnprocessableEntityException(Constants.EXISTENT_PERSON.formatted(personDTO.getSurname()));
            }

            personDTO.setId(UUID.randomUUID());
            personRepository.save(PersonMapper.parseEntity(personDTO));
            return CompletableFuture.completedFuture(personDTO);

        } catch (UnprocessableEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error("Database error: ", e.getCause());
            throw new DatabaseException(Constants.SAVE_ERROR.formatted(personDTO.getSurname()));
        }

    }

    @Override
    public PersonCountDTO personCount() {
        return new PersonCountDTO(personRepository.findAndCountPersons());
    }

}
