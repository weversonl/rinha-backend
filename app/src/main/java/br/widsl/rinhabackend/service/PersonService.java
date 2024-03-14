package br.widsl.rinhabackend.service;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<PersonDTO> findById(String id);

    Flux<PersonDTO> findByTerm(String term);

    Mono<PersonDTO> savePerson(PersonDTO personDTO);

    Mono<PersonCountDTO> personCount();

    Flux<Void> cleanUpPersons();

}
