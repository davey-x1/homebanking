package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

/* ------------------------------------------- */

@Entity
public class CardEntity {
    /* --------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String cardType;

    private String cardColor;

    private long number;

    private int cvv;

    private LocalDate fromDate;

    private LocalDate thruDate;

    @JsonBackReference(value = "cardsOwnedReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private ClientEntity cardOwner;



    /* --------------------------------------- */

    public CardEntity() {

    }

    /* --------------------------------------- */

    public long getId(){
       return id;
    }

    public ClientEntity getCardOwner(){
           return cardOwner;
    }
    public long getNumber(){
       return number;
    }

    public int getCvv(){
       return cvv;
   }

    public LocalDate getFromDate(){
       return fromDate;
    }

    public LocalDate getThruDate(){
       return thruDate;
    }

    public String getCardType(){
       return cardType;
    }

    public String getCardColor(){
       return cardColor;
    }

    /* --------------------------------------- */

    public void setCardOwner(ClientEntity client){
        this.cardOwner = client;
    }

    public void setNumber(long number){
        this.number = number;
    }

    public void setCvv(int cvv){
        this.cvv = cvv;
    }

    public void setThruDate(LocalDate thruDate){
        this.thruDate = thruDate;
    }

    public void setFromDate(LocalDate fromDate){
        this.fromDate = fromDate;
    }

    public void setCardType(String type){
        this.cardType = type;
    }

    public void setCardColor(String color) {
        this.cardColor = color;
    }

}

