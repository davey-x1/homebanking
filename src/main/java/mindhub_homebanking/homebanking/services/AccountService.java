package mindhub_homebanking.homebanking.services;
/* ---------------------------------- */

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.AccountRepository;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionService transactionService;
    public List<AccountEntity> getAllAcounts()
    {
        List<AccountEntity> accountsList = repository.findAll();

        if(accountsList.size() > 0) {
            return accountsList;
        } else {
            return new ArrayList<AccountEntity>();
        }
    }

    public AccountEntity getAccountById(Long id) {
        Optional<AccountEntity> accountFound = repository.findById(id);

        if(accountFound.isPresent()) {
            return accountFound.get();
        } else {
            System.out.println("No account record exist for given id");
            return null;
        }

    }

    public AccountEntity getAccountByNumber(String number) {
        Optional<AccountEntity> accountFound = repository.findByNumber(number);

        if(accountFound.isPresent()) {
            return accountFound.get();
        } else {
            System.out.println("No account record exist for given number");
            return null;
        }

    }

    public AccountEntity createOrUpdateAccount(AccountEntity entity) {
        System.out.println("Found2");
        LocalDate fecha = LocalDate.now();
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String numberOfAccount = "VIN" + String.format("%04d", number);
        Optional<AccountEntity> account = repository.findById(entity.getId());
        if(account.isPresent()){
            AccountEntity newEntity = account.get();
            ClientEntity clientEntity = clientRepository.getById(entity.getAccountOwner().getId());
            newEntity.setCreationDateOfAccount(entity.getCreationDateOfAccount());
            newEntity.setBalanceOfAccount(entity.getBalanceOfAccount());
            newEntity.setNumberOfAccount(entity.getNumberOfAccount());
            newEntity.setAccountType(entity.getAccountType());
            clientEntity.addAccountsOwned(newEntity);
            clientRepository.save(clientEntity);

            newEntity = repository.save(newEntity);

            return newEntity;
        } else {
            System.out.println(entity.getAccountType());
            ClientEntity clientEntity = clientRepository.getById(entity.getAccountOwner().getId());
            entity.setCreationDateOfAccount(fecha);
            entity.setNumberOfAccount(numberOfAccount);
            entity.setBalanceOfAccount(0L);
            entity.setAccountType(entity.getAccountType());
            clientEntity.addAccountsOwned(entity);
            clientRepository.save(clientEntity);
            entity = repository.save(entity);

            return entity;
        }
    }
    public void deleteAccountById(Long id) {
        Optional<AccountEntity> account = repository.findById(id);
        if(account.isPresent()) {
            Set<TransactionEntity> listOfTransactions = account.get().getTransactionEntities();
            List<TransactionEntity> list = new ArrayList<TransactionEntity>(listOfTransactions);
            for(int i = 0; i < list.size(); i++){
                TransactionEntity array = list.get(i);
                transactionService.deleteTransactionById(array.getId());
            }
            repository.deleteById(id);
        }
    }
}