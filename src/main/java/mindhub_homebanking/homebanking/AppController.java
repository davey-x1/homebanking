package mindhub_homebanking.homebanking;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.AccountRepository;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@RestController

public class AppController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {


        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }


        if (clientRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("Email already in use: " + email, HttpStatus.CONFLICT);
        }
        LocalDate fecha = LocalDate.now();
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String numberOfAccount = "VIN" + String.format("%04d", number);

        ClientEntity newClient = new ClientEntity();
        newClient.setFirstName(firstName);
        newClient.setLastName(lastName);
        newClient.setEmail(email);
        newClient.setPassword(passwordEncoder.encode(password));
        newClient.setRole("CLIENT");

        AccountEntity newAccount = new AccountEntity();
        newAccount.setBalanceOfAccount(0);
        newAccount.setNumberOfAccount(numberOfAccount);
        newAccount.setOwnerOfAccount(newClient);
        newAccount.setCreationDateOfAccount(fecha);
        newAccount.setAccountType("SAVINGS");
        newClient.addAccountsOwned(newAccount);
        clientRepository.save(newClient);
        accountRepository.save(newAccount);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "api/clients/current", method = RequestMethod.GET)
    public ClientEntity getCurrentSession(Authentication authentication){
        authentication.getName();
        Optional<ClientEntity> clientOptional = clientRepository.findByEmail(authentication.getName());
        if(clientOptional.isPresent()){
            ClientEntity client = clientOptional.get();
            System.out.println(client.getAccountsOwned());
            return client;
        }
        return null;
    }

}