package br.widsl.rinhabackend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;

public interface PersonService {

    PersonDTO findById(String id);

    List<PersonDTO> findByTerm(String term);

    CompletableFuture<PersonDTO> savePerson(PersonDTO personDTO);

    PersonCountDTO personCount();

}
