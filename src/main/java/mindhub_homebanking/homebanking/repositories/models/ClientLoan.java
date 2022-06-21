package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/* ------------------------------------------- */

@Entity
public class ClientLoan {
    /* --------------------------------------- */
    //id, name, maxAmount y payments

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int amount;

    private int payments;

    @JsonBackReference(value = "ownerOfLoanReference")
    @ManyToOne
    @JoinColumn(name = "client_id")
    ClientEntity ownerOfLoan;

    @JsonBackReference(value = "loanEntityReference")
    @ManyToOne
    @JoinColumn(name = "loan_id")
    LoanEntity loanEntity;

    /* --------------------------------------- */

    public ClientLoan() {
    }

    /* --------------------------------------- */
    //              SETTERS
    /* --------------------------------------- */

    public void setAmountOfLoan(int amount){
        this.amount = amount;
    }

    public void setPayments(int payments){
        this.payments = payments;
    }

    public void setOwnerOfLoan(ClientEntity client){
        this.ownerOfLoan = client;
    }

    public void setLoanEntity(LoanEntity loan){
        this.loanEntity = loan;
    }

    /* --------------------------------------- */
    //              GETTERS
    /* --------------------------------------- */
    public long getId(){
        return id;
    }

    public int getAmountOfLoan(){
        return amount;
    }

    public int getPayments(){
        return payments;
    }

    public LoanEntity getLoanEntity() {
        return loanEntity;
    }

    public ClientEntity getOwnerOfLoan() {
        return ownerOfLoan;
    }
}

