package mindhub_homebanking.homebanking.services;

/* ---------------------------------- */

import java.time.LocalDate;
import java.util.*;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;
import mindhub_homebanking.homebanking.repositories.AccountRepository;
import mindhub_homebanking.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    AccountRepository accountRepository;

    public List<TransactionEntity> getAllTransactions()
    {
        List<TransactionEntity> transactionList = repository.findAll();

        if(transactionList.size() > 0) {
            return transactionList;
        } else {
            return new ArrayList<TransactionEntity>();
        }
    }

    public TransactionEntity getTransactionById(Long id)
    {
        Optional<TransactionEntity> transaction = repository.findById(id);

        if(transaction.isPresent()) {

            return transaction.get();

        } else {
            System.out.println("No transaction record exist for given id");
            return null;
        }

    }

    public TransactionEntity createOrUpdateTransaction(TransactionEntity entity)
    {
        LocalDate fecha = LocalDate.now();
        Optional<TransactionEntity> account = repository.findById(entity.getId());
        if(account.isPresent())
        {
            TransactionEntity newEntity = account.get();
            AccountEntity accountEntity = newEntity.getAccount();
            newEntity.setDate(entity.getDate());
            newEntity.setAmount(entity.getAmount());
            newEntity.setDescription(entity.getDescription());
            accountEntity.addTransaction(newEntity);
            accountRepository.save(accountEntity);
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity.setDate(fecha);
            AccountEntity accountEntity = entity.getAccount();
            accountEntity.addTransaction(entity);
            accountRepository.save(accountEntity);
            entity = repository.save(entity);
            return entity;
        }
    }

    public Set<TransactionEntity> createTransaction(int monto, String description, String account1, String account2){
        LocalDate fecha = LocalDate.now();
        Optional<AccountEntity> cuentaOrigen = accountRepository.findByNumber(account1);
        Optional<AccountEntity> cuentaDestino = accountRepository.findByNumber(account2);
        Set<TransactionEntity> transaccionesFinales = new HashSet<>();
        if(cuentaOrigen.isPresent() && cuentaDestino.isPresent()){
            TransactionEntity newTransaction1 = new TransactionEntity();
            TransactionEntity newTransaction2 = new TransactionEntity();
            AccountEntity cuenta1 = cuentaOrigen.get();
            AccountEntity cuenta2 = cuentaDestino.get();
            newTransaction1.setDescription(description);
            newTransaction1.setLastAmount(cuenta1.getBalanceOfAccount() - monto);
            cuenta1.setBalanceOfAccount(cuenta1.getBalanceOfAccount() - monto);
            newTransaction1.setAmount(monto);
            newTransaction1.setAccount(cuenta1);
            newTransaction1.setType("DEBIT");
            newTransaction1.setDate(fecha);
            // ---------------------------------
            newTransaction2.setDescription(description);
            newTransaction2.setLastAmount(cuenta2.getBalanceOfAccount() + monto);
            cuenta2.setBalanceOfAccount(cuenta2.getBalanceOfAccount() + monto);
            newTransaction2.setAmount(monto);
            newTransaction2.setAccount(cuenta2);
            newTransaction2.setType("CREDIT");
            newTransaction2.setDate(fecha);
            // ----------------------------------
            cuenta1.addTransaction(newTransaction1);
            accountRepository.save(cuenta1);
            repository.save(newTransaction1);
            // ----------------------------------
            cuenta2.addTransaction(newTransaction2);
            accountRepository.save(cuenta2);
            repository.save(newTransaction2);
            transaccionesFinales.add(newTransaction1);
            transaccionesFinales.add(newTransaction2);
        }
        return transaccionesFinales;
    }

    public TransactionEntity addSingleTransaction(TransactionEntity transaction){
        LocalDate fecha = LocalDate.now();
        Optional<AccountEntity> accountEntity = accountRepository.findById(transaction.getAccount().getId());
        AccountEntity accountUsed = accountEntity.get();
        transaction.setDate(fecha);
        if(transaction.getType() == "CREDIT"){
            transaction.setLastAmount(accountUsed.getBalanceOfAccount() + transaction.getAmount());
            accountUsed.setBalanceOfAccount(accountUsed.getBalanceOfAccount() + transaction.getAmount());
        } else {
            transaction.setLastAmount(accountUsed.getBalanceOfAccount() - transaction.getAmount());
            accountUsed.setBalanceOfAccount(accountUsed.getBalanceOfAccount() - transaction.getAmount());
        }
        TransactionEntity finalTransaction = repository.save(transaction);
        accountUsed.addTransaction(finalTransaction);
        accountRepository.save(accountUsed);
        return finalTransaction;
    }

    public void deleteTransactionById(Long id) {
        Optional<TransactionEntity> transaction = repository.findById(id);
        if(transaction.isPresent()) {
            repository.deleteById(id);
        }
    }
}