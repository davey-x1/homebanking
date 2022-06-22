package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
/* ------------------------------------------- */

@Entity
public class ClientLoanEntity {
    /* --------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @JoinColumn(name = "fk_loan_id")
    private int fk_loanid;

    private String name;
    private int amount;
    private int paymentOfLoans;

    @JsonBackReference(value = "ownerOfLoanClientReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    ClientEntity ownerOfLoan;
    @JsonBackReference(value = "clientLoanEntityReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    LoanEntity loanEntity;

    /* --------------------------------------- */

    public ClientLoanEntity() {
    }

    /* --------------------------------------- */
    //              SETTERS
    /* --------------------------------------- */

    public void setAmountOfLoan(int amount){
        this.amount = amount;
    }

    public void setPaymentOfLoans(int payments){
        this.paymentOfLoans = payments;
    }

    public void setLoanId(int loanId){
        this.fk_loanid = loanId;
    }

    public void setNameOfLoan(String name){
        this.name = name;
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

    public int getPaymentOfLoans(){
        return paymentOfLoans;
    }
    public LoanEntity getLoanEntity() {
        return loanEntity;
    }
    public ClientEntity getOwnerOfLoan() {
        return ownerOfLoan;
    }

    public String getNameOfLoan(){
        return name;
    }

    public int getIdOfLoan(){
        return fk_loanid;
    }
}

