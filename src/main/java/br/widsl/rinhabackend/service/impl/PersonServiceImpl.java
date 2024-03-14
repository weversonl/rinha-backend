package br.widsl.rinhabackend.service.impl;

import static br.widsl.rinhabackend.constants.Constants.DATE_PATTERN;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.exception.impl.BadRequestException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import br.widsl.rinhabackend.mapper.PersonMapper;
import br.widsl.rinhabackend.repository.PersonRepository;
import br.widsl.rinhabackend.service.PersonService;
import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;

@Service
@CacheConfig(cacheNames = "persons-cache")
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Cacheable("#surname")
    private Mono<String> findBySurname(final String surname) {
        return personRepository.findBySurname(surname)
                .map(person -> {
                    if (person != null) {
                        throw new UnprocessableEntityException();
                    }
                    return person;
                });
    }

    @Override
    @Cacheable("#id")
    public Mono<PersonDTO> findById(final String id) {

        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException();
        }

        return this.personRepository.findById(requireNonNull(uuid))
                .map(PersonMapper::parseDTO)
                .switchIfEmpty(Mono.error(new PersonNotFound()));

    }

    @Override
    public Flux<PersonDTO> findByTerm(final String term) {

        if (term == null || term.isEmpty())
            return Flux.empty();

        if (DATE_PATTERN.matcher(term).matches())
            return this.personRepository.findByDate(LocalDate.parse(term))
                    .map(PersonMapper::parseDTO);
        else
            return this.personRepository.findByTerm(term)
                    .map(PersonMapper::parseDTO);

    }

    @Override
    @Transactional
    public Mono<PersonDTO> savePerson(PersonDTO personDTO) {

        return this.findBySurname(personDTO.getSurname())
                .then(Mono.defer(() -> {
                    personDTO.setId(UUID.randomUUID());
                    return this.personRepository.save(PersonMapper.parseEntity(personDTO))
                            .thenReturn(personDTO);
                }));

    }

    @Override
    public Mono<PersonCountDTO> personCount() {
        return this.personRepository.findAndCountPersons()
                .map(PersonCountDTO::new);
    }

    @Override
    public Flux<Void> cleanUpPersons() {
        return this.personRepository.findByTerm("warmup")
                .flatMap(t -> this.personRepository.deleteById(requireNonNull(t.getId())));
    }

}
