package br.widsl.rinhabackend.repository;

import br.widsl.rinhabackend.domain.entity.PersonEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    @Transactional(readOnly = true)
    @Query("SELECT p.surname FROM persons p WHERE p.surname = :surname")
    Optional<String> findBySurname(@Param("surname") String surname);

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(1) FROM persons")
    Integer findAndCountPersons();

    @Transactional(readOnly = true)
    @NonNull Optional<PersonEntity> findById(@NonNull UUID id);

    @Transactional(readOnly = true)
    @Query("""
                SELECT p.id, p.name, p.surname, p.birth, p.stack FROM persons p
                WHERE p.terms ILIKE '%' || :termo || '%'
                LIMIT 50
            """)
    List<PersonEntity> findByTerm(@Param("termo") String term);

    @Transactional(readOnly = true)
    @Query("SELECT p.id, p.name, p.surname, p.birth, p.stack FROM persons p WHERE p.birth = :date")
    List<PersonEntity> findByDate(@Param("date") LocalDate date);

}
