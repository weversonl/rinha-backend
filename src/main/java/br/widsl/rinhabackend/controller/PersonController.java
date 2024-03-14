package br.widsl.rinhabackend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.service.PersonService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/pessoas")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<PersonDTO>> savePerson(@RequestBody @Valid PersonDTO requestDTO) {

        return this.personService.savePerson(requestDTO)
                .map(response -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Location", "/pessoas/%s".formatted(response.getId()));
                    return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
                });

    }

    @GetMapping("/pessoas/{id}")
    public Mono<PersonDTO> getPersonById(@PathVariable String id) {
        return this.personService.findById(id);
    }

    @GetMapping("/pessoas")
    public Flux<PersonDTO> getPersonByTerm(@RequestParam(name = "t", required = true) String term) {
        return this.personService.findByTerm(term);
    }

    @GetMapping("/contagem-pessoas")
    public Mono<PersonCountDTO> personCount() {
        return this.personService.personCount();
    }

}
