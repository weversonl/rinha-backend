package br.widsl.rinhabackend.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import br.widsl.rinhabackend.domain.entity.PersonEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<PersonEntity, UUID> {

    @Transactional(readOnly = true)
    @Query("SELECT p.surname FROM persons p WHERE p.surname = :surname")
    Mono<String> findBySurname(@Param("surname") String surname);

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(1) FROM persons")
    Mono<Integer> findAndCountPersons();

    @Transactional(readOnly = true)
    @NonNull
    Mono<PersonEntity> findById(@NonNull UUID id);

    @Transactional(readOnly = true)
    @Query("""
                SELECT p.id, p.name, p.surname, p.birth, p.stack FROM persons p
                WHERE p.terms ILIKE '%' || :termo || '%'
                LIMIT 50
            """)
    Flux<PersonEntity> findByTerm(@Param("termo") String term);

    @Transactional(readOnly = true)
    @Query("SELECT p.id, p.name, p.surname, p.birth, p.stack FROM persons p WHERE p.birth = :date")
    Flux<PersonEntity> findByDate(@Param("date") LocalDate date);

}
