package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/* ------------------------------------------- */

@Entity
public class LoanEntity {
    /* --------------------------------------- */
    //id, name, maxAmount y payments
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private int maxAmount;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payments", joinColumns = @JoinColumn(name = "payments_id"))
    private Set<Integer> payments = new HashSet<>();

    @JsonBackReference(value = "clientLoanEntityReference")
    @OneToMany(mappedBy = "loanEntity", fetch=FetchType.EAGER)
    private Set<ClientLoanEntity> loansOwned = new HashSet<>();


    /* --------------------------------------- */

    public LoanEntity() {
    }

    /* --------------------------------------- */
    //              SETTERS
    /* --------------------------------------- */

    public void setNameOfLoan(String name){
        this.name = name;
    }

    public void setMaxAmountOfLoan(int maxAmount){
        this.maxAmount = maxAmount;
    }

    public void addToPaymentsLoan(int payment){
        this.payments.add(payment);
    }

    public void setLoansOwned(ClientLoanEntity loan){
        this.loansOwned.add(loan);
    }

    /* --------------------------------------- */
    //              GETTERS
    /* --------------------------------------- */

    public long getId(){
        return id;
    }

    public String getNameOfLoan(){
        return name;
    }

    public int getMaxAmountOfLoan(){
        return maxAmount;
    }

    public Set<Integer> getPaymentsOfLoan(){
        return payments;
    }

    public Set<ClientLoanEntity> getLoansOwned(){
        return loansOwned;
    }

}

