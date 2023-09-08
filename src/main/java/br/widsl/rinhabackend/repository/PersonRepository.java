package br.widsl.rinhabackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.widsl.rinhabackend.domain.entity.PersonEntity;

public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<PersonEntity> findBySurname(String surname);

    @Transactional(readOnly = true)
    List<PersonEntity> findAll();

    @Transactional(readOnly = true)
    Optional<PersonEntity> findById(UUID id);

    @Transactional(readOnly = true)
    @Query("""
        SELECT * FROM persons p
        WHERE p.name LIKE '%' || :termo || '%'
        OR p.surname LIKE '%' || :termo || '%'
        OR array_to_string(p.stack, ',') ILIKE '%' || :termo || '%'
    """)
    List<PersonEntity> findByTerm(@Param("termo") String term);

    @Transactional(readOnly = true)
    @Query("SELECT * FROM persons p WHERE p.birth = :date")
    List<PersonEntity> findByDate(@Param("date") LocalDate date);

}
