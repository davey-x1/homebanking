package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/* ---------------------------------- */

@RepositoryRestResource
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByNumber(long number);

}