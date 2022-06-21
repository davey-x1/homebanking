package mindhub_homebanking.homebanking.dto;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.CardEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {

    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Set<AccountEntity> accountsOwned = new HashSet<>();
    public Set<CardEntity> cardsOwned = new HashSet<>();
    public Set<ClientLoan> loansOwned = new HashSet<>();

    public ClientDTO(ClientEntity client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.accountsOwned = client.getAccounts();
        this.cardsOwned = client.getCards();
        this.loansOwned = client.getLoansOwned();
    }
}
