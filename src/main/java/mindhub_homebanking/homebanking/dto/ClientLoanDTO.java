package mindhub_homebanking.homebanking.dto;

import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;

public class ClientLoanDTO {
    public long id;
    public ClientEntity ownerOfLoan;
    public int amount;
    public int payments;

    public ClientLoanDTO(ClientLoan loan) {
        this.id = loan.getId();
        this.amount = loan.getAmountOfLoan();
        this.ownerOfLoan = loan.getOwnerOfLoan();
        this.payments = loan.getPayments();
    }
}
