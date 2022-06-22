package mindhub_homebanking.homebanking.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {
    public int fk_loanid;
    public long id;
    public String name;
    public int amount;
    public int paymentOfLoans;
    private ClientEntity ownerOfLoan;
    private LoanEntity loanEntity;
    public ClientLoanDTO(ClientLoanEntity loan) {
        this.id = loan.getId();
        this.fk_loanid = loan.getIdOfLoan();
        this.name = loan.getNameOfLoan();
        this.amount = loan.getAmountOfLoan();
        this.paymentOfLoans = loan.getPaymentOfLoans();
        this.ownerOfLoan = loan.getOwnerOfLoan();
        this.loanEntity = loan.getLoanEntity();
    }
}
