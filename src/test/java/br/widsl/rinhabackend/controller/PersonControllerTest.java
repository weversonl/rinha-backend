package br.widsl.rinhabackend.controller;

import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper;

    private PersonDTO validPersonDTO;

    @BeforeEach
    void setUp() {
        validPersonDTO = new PersonDTO("John", "JohnL", "2002-01-22", new String[]{"Java"});
        validPersonDTO.setId(UUID.randomUUID());
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSavePersonWhenExecutionExceptionThenThrowsTechnicalException() throws Exception {
        Mockito.when(personService.savePerson(Mockito.any(PersonDTO.class)))
                .thenReturn(CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException(new ExecutionException(new Exception()));
                }));

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .content(new ObjectMapper().writeValueAsString(validPersonDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void testSavePersonWhenInterruptedExceptionThenThrowsTechnicalExceptionAndInterruptsThread() throws Exception {
        Mockito.when(personService.savePerson(Mockito.any(PersonDTO.class)))
                .thenReturn(CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException(new InterruptedException());
                }));

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .content(new ObjectMapper().writeValueAsString(validPersonDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

        Assertions.assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    void testSavePersonWhenDataIsValidThenReturnsCreated() throws Exception {
        Mockito.when(personService.savePerson(Mockito.any(PersonDTO.class)))
                .thenReturn(CompletableFuture.completedFuture(validPersonDTO));

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .content(new ObjectMapper().writeValueAsString(validPersonDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/pessoas/" + validPersonDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validPersonDTO.getId().toString()));
    }

    @Test
    void testPersonCountWhenCalledThenPersonServicePersonCountIsCalled() throws Exception {
        PersonCountDTO personCountDTO = new PersonCountDTO(5);
        Mockito.when(personService.personCount()).thenReturn(personCountDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/contagem-pessoas"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(personService, Mockito.times(1)).personCount();
    }

    @Test
    void testPersonCountWhenCalledThenReturnsCorrectResponseEntity() throws Exception {
        PersonCountDTO personCountDTO = new PersonCountDTO(5);
        Mockito.when(personService.personCount()).thenReturn(personCountDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/contagem-pessoas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"persons\":5}"));
    }

    @Test
    void testGetPersonByTermWhenTermIsGivenThenReturnListOfPersonDTOs() throws Exception {
        String term = "John";
        List<PersonDTO> personDTOs = Arrays.asList(
                new PersonDTO("Doe", "John", "2000-01-01", new String[]{"Java", "Spring"}),
                new PersonDTO("Smith", "John", "1999-01-01", new String[]{"Python", "Django"}));
        Mockito.when(personService.findByTerm(term)).thenReturn(personDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas").param("t", term))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(personDTOs)));
    }

    @Test
    void testGetPersonByTermWhenNotFoundPersonsByTerm() throws Exception {
        String term = "John";
        Mockito.when(personService.findByTerm(term)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas").param("t", term))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
        ;
    }

    @Test
    void testGetPersonByIdWhenIdIsValidThenReturnsCorrectPersonDTO() throws Exception {
        String id = UUID.randomUUID().toString();
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[]{"Java", "Spring"});
        personDTO.setId(UUID.fromString(id));
        Mockito.when(personService.findById(id)).thenReturn(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(personDTO)));
    }

    @Test
    void testGetPersonByIdWhenIdDoesNotExistThenThrowsException() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(personService.findById(id)).thenThrow(new PersonNotFound("Person not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}