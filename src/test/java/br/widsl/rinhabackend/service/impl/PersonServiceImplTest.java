package br.widsl.rinhabackend.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import br.widsl.rinhabackend.exception.impl.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;
import br.widsl.rinhabackend.exception.impl.DatabaseException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        reset(personRepository);
    }

    @Test
    void testSavePersonWhenSurnameIsUniqueThenReturnSavedPerson() {
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        PersonEntity personEntity = new PersonEntity(null, "Doe", "John", LocalDate.now(), new String[] { "Java", "Spring" });
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

        CompletableFuture<PersonDTO> result = personService.savePerson(personDTO);

        assertEquals(personDTO.getSurname(), result.join().getSurname());
    }

    @Test
    void testSavePersonWhenSurnameIsNotUniqueThenThrowBadRequestException() {
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        String personEntity = "surname";

        when(personRepository.findBySurname(anyString())).thenReturn(Optional.of(personEntity));

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessageContaining(Constants.EXISTENT_PERSON.formatted(personDTO.getSurname()));
    }

    @Test
    void testSavePersonWhenDatabaseErrorOccursThenThrowDatabaseException() {
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(PersonEntity.class))).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(DatabaseException.class)
                .hasMessageContaining(Constants.SAVE_ERROR.formatted(personDTO.getSurname()));
    }

    @Test
    void testPersonCountWhenPersonsExistThenReturnCorrectCount() {
        when(personRepository.findAndCountPersons()).thenReturn(2);
        PersonCountDTO result = personService.personCount();
        assertEquals(2, result.getPersons());
    }

    @Test
    void testPersonCountWhenNoPersonsExistThenReturnZero() {
        when(personRepository.findAndCountPersons()).thenReturn(0);

        PersonCountDTO result = personService.personCount();

        assertEquals(0, result.getPersons());
    }

    @Test
    void testFindByIdWhenIdExistsThenReturnsPersonDTO() {
        UUID id = UUID.randomUUID();
        PersonEntity personEntity = new PersonEntity(null, "Doe", "John", LocalDate.now(), new String[] { "Java", "Spring" });
        personEntity.setId(id);
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(personEntity));

        PersonDTO result = personService.findById(id.toString());

        assertEquals(id, result.getId());
    }

    @Test
    void testFindByIdWhenIdDoesNotExistThenThrowsPersonNotFound() {
        String id = UUID.randomUUID().toString();
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.findById(id))
                .isInstanceOf(PersonNotFound.class)
                .hasMessageContaining(Constants.EMPTY_PERSON.formatted(id));
    }

    @Test
    void testFindByTermWhenTermIsDateThenFindByDateIsCalled() {
        String term = "2022-01-01";
        when(personRepository.findByDate(any(LocalDate.class))).thenReturn(List.of());

        personService.findByTerm(term);

        verify(personRepository, times(1)).findByDate(any(LocalDate.class));
    }

    @Test
    void testFindByTermWhenTermIsNotDateThenFindByTermIsCalled() {
        String term = "Java";
        when(personRepository.findByTerm(anyString())).thenReturn(List.of());

        personService.findByTerm(term);

        verify(personRepository, times(1)).findByTerm(anyString());
    }

    @Test
    void testSavePersonWhenPersonDoesNotExistThenReturnPerson() throws ExecutionException, InterruptedException {
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });

        when(personRepository.findBySurname(anyString())).thenReturn(Optional.empty());

        CompletableFuture<PersonDTO> result = personService.savePerson(personDTO);

        assertEquals(personDTO.getSurname(), result.get().getSurname());
    }

    @Test
    void testSavePersonWhenPersonExistsThenThrowBadRequestException() {
        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        String personEntity = "surname";
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.of(personEntity));

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessageContaining(Constants.EXISTENT_PERSON.formatted(personDTO.getSurname()));
    }

    @Test
    void testPersonCountWhenRepositoryReturnsPersonsThenCorrectCountIsReturned() {
        when(personRepository.findAndCountPersons()).thenReturn(2);
        PersonCountDTO result = personService.personCount();
        assertEquals(2, result.getPersons());
    }

    @Test
    void testPersonCountWhenRepositoryReturnsNoPersonsThenCountIsZero() {
        when(personRepository.findAndCountPersons()).thenReturn(0);
        PersonCountDTO result = personService.personCount();
        assertEquals(0, result.getPersons());
    }

    @Test
    void testSavePersonWhenSurnameExistsThenThrowsExistentPersonException() {

        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        String personEntity = "surname";
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.of(personEntity));

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessageContaining(Constants.EXISTENT_PERSON.formatted(personDTO.getSurname()));
    }

    @Test
    void testSavePersonWhenExceptionOccursThenThrowsDatabaseException() {

        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(PersonEntity.class))).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(DatabaseException.class)
                .hasMessageContaining(Constants.SAVE_ERROR.formatted(personDTO.getSurname()));
    }

    @Test
    void testFindByTermWhenTermIsDateThenRepositoryFindByDateIsCalled() {

        String term = "2022-01-01";
        when(personRepository.findByDate(any(LocalDate.class))).thenReturn(List.of());

        personService.findByTerm(term);

        verify(personRepository, times(1)).findByDate(any(LocalDate.class));
    }

    @Test
    void testFindByTermWhenTermIsNotDateThenRepositoryFindByTermIsCalled() {

        String term = "Java";
        when(personRepository.findByTerm(anyString())).thenReturn(List.of());

        personService.findByTerm(term);

        verify(personRepository, times(1)).findByTerm(anyString());
    }
}