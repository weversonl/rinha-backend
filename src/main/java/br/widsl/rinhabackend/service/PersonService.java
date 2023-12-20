package br.widsl.rinhabackend.service;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersonService {

    PersonDTO findById(String id);

    List<PersonDTO> findByTerm(String term);

    CompletableFuture<PersonDTO> savePerson(PersonDTO personDTO);

    PersonCountDTO personCount();

}
