package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/* ---------------------------------- */

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


}