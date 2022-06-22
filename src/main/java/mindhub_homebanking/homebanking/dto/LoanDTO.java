package mindhub_homebanking.homebanking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class LoanDTO {
    public long id;
    public String name;
    public int maxAmount;
    public Set<Integer> payments = new HashSet<>();
    public Set<ClientLoanEntity> loansOwned = new HashSet<>();

    public LoanDTO(LoanEntity loanEntity) {
        this.id = loanEntity.getId();
        this.name = loanEntity.getNameOfLoan();
        this.maxAmount = loanEntity.getMaxAmountOfLoan();
        this.payments = loanEntity.getPaymentsOfLoan();
        this.loansOwned = loanEntity.getLoansOwned();
    }

}
