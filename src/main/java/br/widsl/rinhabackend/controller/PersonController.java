package br.widsl.rinhabackend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.service.PersonService;
import jakarta.validation.Valid;

@RestController
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/pessoas")
    public ResponseEntity<PersonDTO> savePerson(@RequestBody @Valid PersonDTO requestDTO) {

        log.info("Save endpoint | Request -> {}", requestDTO);
        PersonDTO response = personService.savePerson(requestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/pessoas/%s".formatted(response.getId()));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable String id) {
        log.info("Get by Id endpoint | Id -> {}", id);
        PersonDTO response = personService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PersonDTO>> getPersonByTerm(@RequestParam(name = "t", required = false) String term) {
        log.info("Get by Term endpoint | Id -> {}", term);
        List<PersonDTO> response = personService.findByTerm(term);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<PersonCountDTO> personCount() {
        log.info("Person Count Term endpoint");
        return ResponseEntity.ok(personService.personCount());
    }

}
