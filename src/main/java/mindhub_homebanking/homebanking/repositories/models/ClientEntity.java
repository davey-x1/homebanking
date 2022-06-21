package mindhub_homebanking.homebanking.repositories.models;
/* ------------------------------------------- */
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/* ------------------------------------------- */

@Entity
public class ClientEntity {
    /* --------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    @JsonManagedReference(value = "accountsOwnedReference")
    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    Set<AccountEntity> accountsOwned = new HashSet<>();


    @OneToMany(mappedBy = "owner", fetch=FetchType.EAGER)
    Set<CardEntity> cardsOwned = new HashSet<>();

    @JsonManagedReference(value = "ownerOfLoanReference")
    @OneToMany(mappedBy = "ownerOfLoan", fetch=FetchType.EAGER)
    Set<ClientLoan> loansOwned = new HashSet<>();

    /* --------------------------------------- */

    public ClientEntity() {
    }

    public ClientEntity(String firstName, String lastName, String email, String pass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = pass;
    }

    /* --------------------------------------- */

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }

    public long getId(){
        return id;
    }
    public Set<AccountEntity> getAccounts(){
        return accountsOwned;
    }
    public Set<CardEntity> getCards(){
        return cardsOwned;
    }

    public Set<ClientLoan> getLoansOwned(){
        return loansOwned;
    }

    /* --------------------------------------- */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setEmail(String mail){
        this.email = mail;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setLoansOwned(ClientLoan entity){
        this.loansOwned.add(entity);
    }

    public void setRole(String role){
        this.role = role;
    }

    public void addAccount(AccountEntity account){
        this.accountsOwned.add(account);
    }

    public void addCard(CardEntity card){
        this.cardsOwned.add(card);
    }
}

