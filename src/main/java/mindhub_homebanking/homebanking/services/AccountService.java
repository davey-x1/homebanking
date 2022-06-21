package mindhub_homebanking.homebanking.services;
/* ---------------------------------- */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.AccountRepository;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;
    @Autowired
    ClientRepository clientRepository;
    public List<AccountEntity> getAllAcounts()
    {
        List<AccountEntity> accountsList = repository.findAll();

        if(accountsList.size() > 0) {
            return accountsList;
        } else {
            return new ArrayList<AccountEntity>();
        }
    }

    public AccountEntity getAccountById(Long id)
    {
        Optional<AccountEntity> accountFound = repository.findById(id);

        if(accountFound.isPresent()) {
            return accountFound.get();
        } else {
            System.out.println("No account record exist for given id");
            return null;
        }

    }

    public AccountEntity getAccountByNumber(String number)
    {
        Optional<AccountEntity> accountFound = repository.findByNumber(number);

        if(accountFound.isPresent()) {
            return accountFound.get();
        } else {
            System.out.println("No account record exist for given number");
            return null;
        }

    }

    public AccountEntity createOrUpdateAccount(AccountEntity entity)
    {
        System.out.println("Found2");
        LocalDate fecha = LocalDate.now();
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String numberOfAccount = "VIN" + String.format("%04d", number);
        Optional<AccountEntity> account = repository.findById(entity.getId());
        if(account.isPresent())
        {
            AccountEntity newEntity = account.get();
            ClientEntity clientEntity = newEntity.getOwner();
            newEntity.setCreationDateOfAccount(entity.getCreationDateOfAccount());
            newEntity.setBalanceOfAccount(entity.getBalanceOfAccount());
            newEntity.setNumberOfAccount(entity.getNumberOfAccount());
            newEntity.setAccountType(entity.getAccountType());
            clientEntity.addAccount(newEntity);
            clientRepository.save(clientEntity);

            newEntity = repository.save(newEntity);

            return newEntity;
        } else {
            System.out.println(entity.getAccountType());
            ClientEntity clientEntity = entity.getOwner();
            entity.setCreationDateOfAccount(fecha);
            entity.setNumberOfAccount(numberOfAccount);
            entity.setBalanceOfAccount(0L);
            entity.setAccountType(entity.getAccountType());
            clientEntity.addAccount(entity);
            clientRepository.save(clientEntity);
            entity = repository.save(entity);

            return entity;
        }
    }

    public void deleteAccountById(Long id)
    {
        Optional<AccountEntity> account = repository.findById(id);
        if(account.isPresent())
        {
            repository.deleteById(id);
        }
    }
}