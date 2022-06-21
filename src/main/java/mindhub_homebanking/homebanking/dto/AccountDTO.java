package mindhub_homebanking.homebanking.dto;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {
    private long id;
    public String accountType;
    private String number;
    private LocalDate creationDate;
    public long balance;
    public Set<TransactionEntity> transactions = new HashSet<>();

    public AccountDTO(AccountEntity account) {

        this.id = account.getId();

        this.number = account.getNumberOfAccount();

        this.creationDate = account.getCreationDateOfAccount();

        this.balance = account.getBalanceOfAccount();



        this.transactions = account.getTransactionEntities();

        this.accountType = account.getAccountType();
    }
}
