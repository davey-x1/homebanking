package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

/* ------------------------------------------- */

@Entity
public class TransactionEntity {
    /* --------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;
    private int amount;
    private String description;
    private LocalDate date;

    @JsonBackReference(value = "transactionEntitiesReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    private AccountEntity account;

    /* --------------------------------------- */

    public TransactionEntity() {

    }

    /* --------------------------------------- */

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription(){ return description; }

    public LocalDate getDate(){
        return date;
    }

    public AccountEntity getAccount(){
        return account;
    }

    /* --------------------------------------- */

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAccount(AccountEntity account){
        this.account = account;
    }
}

