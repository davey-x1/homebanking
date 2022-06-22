package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
/* ------------------------------------------- */

@Entity
public class AccountEntity {
    /* --------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDate creationDate;
    private long balance;

    private String accountType;

    @JsonBackReference(value = "accountsOwnedReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="accountOwner_id")
    private ClientEntity accountOwner;

    @JsonManagedReference(value = "accountOfLoanReference")
    @OneToMany(mappedBy="accountOfLoan", fetch=FetchType.EAGER)
    Set<ClientLoanEntity> clientLoanEntities = new HashSet<>();
    @JsonManagedReference(value = "transactionEntitiesReference")
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<TransactionEntity> transactionEntities = new HashSet<>();


    /* --------------------------------------- */
    public AccountEntity() {
    }

    /* --------------------------------------- */
    //              SETTERS

    public void setNumberOfAccount(String num){
        this.number = num;
    }

    public void setCreationDateOfAccount(LocalDate date){
        this.creationDate = date;
    }

    public void setBalanceOfAccount(long num){
        this.balance = num;
    }

    public void setOwnerOfAccount(ClientEntity client){
        this.accountOwner = client;
    }

    public void addTransaction(TransactionEntity transaction){
        transactionEntities.add(transaction);
    }

    public void setAccountType(String type){
       this.accountType = type;
    }

    public void setClientLoanEntities(ClientLoanEntity clientLoan){
        this.clientLoanEntities.add(clientLoan);
    }

    /* --------------------------------------- */
    //              GETTERS

    public String getNumberOfAccount(){
        return number;
    }

    public LocalDate getCreationDateOfAccount(){
        return creationDate;
    }

    public long getBalanceOfAccount(){
        return balance;
    }

    public long getId(){
        return id;
    }

    public ClientEntity getAccountOwner(){
        return accountOwner;
    }

    public Set<TransactionEntity> getTransactionEntities(){
        return transactionEntities;
    }

    public String getAccountType(){
        return this.accountType;

    }

    public Set<ClientLoanEntity> getClientLoanEntities(){
        return clientLoanEntities;
    }
}

