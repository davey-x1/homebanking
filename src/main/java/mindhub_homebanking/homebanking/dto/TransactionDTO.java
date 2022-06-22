package mindhub_homebanking.homebanking.dto;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;

import java.time.LocalDate;

public class TransactionDTO {

    public long id;
    public int type;
    public int amount;
    public String description;
    public LocalDate date;
    public AccountEntity account;

    public long lastAmount;
    public TransactionDTO(TransactionEntity transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.account = transaction.getAccount();
        this.lastAmount = transaction.getLastAmount();
    }
}
