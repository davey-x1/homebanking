package mindhub_homebanking.homebanking.repositories;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoan;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/* ---------------------------------- */

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    Optional<ClientLoan> findByLoanEntity(LoanEntity loanEntity);
}