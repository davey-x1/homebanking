package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/* ---------------------------------- */

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByEmail(String email);
}