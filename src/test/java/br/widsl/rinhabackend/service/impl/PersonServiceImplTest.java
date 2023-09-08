package br.widsl.rinhabackend.service.impl;

import br.widsl.rinhabackend.constants.Constants;
import br.widsl.rinhabackend.domain.dto.PersonCountDTO;
import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.domain.entity.PersonEntity;
import br.widsl.rinhabackend.exception.impl.DatabaseException;
import br.widsl.rinhabackend.exception.impl.ExistentPersonException;
import br.widsl.rinhabackend.exception.impl.PersonNotFound;
import br.widsl.rinhabackend.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    void testPersonCountWhenPersonsExistThenReturnCorrectCount() {

        List<PersonEntity> persons = List.of(new PersonEntity(), new PersonEntity());
        when(personRepository.findAll()).thenReturn(persons);

        PersonCountDTO result = personService.personCount();

        assertEquals(persons.size(), result.getPersons());
    }

    @Test
    void testPersonCountWhenNoPersonsExistThenReturnZero() {

        when(personRepository.findAll()).thenReturn(List.of());

        PersonCountDTO result = personService.personCount();

        assertEquals(0, result.getPersons());
    }

    @Test
    void testPersonCountWhenRepositoryReturnsPersonsThenCorrectCountIsReturned() {

        List<PersonEntity> persons = List.of(new PersonEntity(), new PersonEntity());
        when(personRepository.findAll()).thenReturn(persons);

        PersonCountDTO result = personService.personCount();

        assertEquals(persons.size(), result.getPersons());
    }

    @Test
    void testPersonCountWhenRepositoryReturnsNoPersonsThenCountIsZero() {

        when(personRepository.findAll()).thenReturn(List.of());


        PersonCountDTO result = personService.personCount();


        assertEquals(0, result.getPersons());
    }

    @Test
    void testSavePersonWhenSurnameIsUniqueThenReturnsSavedPerson() {

        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        PersonEntity personEntity = new PersonEntity("Doe", "John", LocalDate.now(), new String[] { "Java", "Spring" });
        personEntity.setId(UUID.randomUUID());
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

        PersonDTO result = personService.savePerson(personDTO);

        assertEquals(personEntity.getId(), result.getId());
    }

    @Test
    void testSavePersonWhenSurnameExistsThenThrowsExistentPersonException() {

        PersonDTO personDTO = new PersonDTO("Doe", "John", "2000-01-01", new String[] { "Java", "Spring" });
        PersonEntity personEntity = new PersonEntity("Doe", "John", LocalDate.now(), new String[] { "Java", "Spring" });
        when(personRepository.findBySurname(anyString())).thenReturn(Optional.of(personEntity));

        assertThatThrownBy(() -> personService.savePerson(personDTO))
                .isInstanceOf(ExistentPersonException.class)
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
    void testFindByIdWhenIdExistsThenReturnsPersonDTO() {

        UUID id = UUID.randomUUID();
        PersonEntity personEntity = new PersonEntity("Doe", "John", LocalDate.now(), new String[] { "Java", "Spring" });
        personEntity.setId(id);
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(personEntity));

        PersonDTO result = personService.findById(id.toString());

        assertNotNull(result);
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

    // ... existing tests ...
}