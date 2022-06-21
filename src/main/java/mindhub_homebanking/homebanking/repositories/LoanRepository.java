package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/* ---------------------------------- */

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {


}