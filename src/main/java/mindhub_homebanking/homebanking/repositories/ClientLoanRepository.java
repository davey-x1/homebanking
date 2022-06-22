package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/* ---------------------------------- */

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoanEntity, Long> {
    Optional<ClientLoanEntity> findByLoanEntity(LoanEntity loanEntity);
}