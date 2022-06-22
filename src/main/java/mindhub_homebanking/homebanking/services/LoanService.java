package mindhub_homebanking.homebanking.services;
/* ---------------------------------- */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.ClientLoanRepository;
import mindhub_homebanking.homebanking.repositories.LoanRepository;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class LoanService {
    @Autowired
    LoanRepository repository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;

    public List<LoanEntity> getAllLoans() {
        List<LoanEntity> accountsList = repository.findAll();

        if(accountsList.size() > 0) {
            return accountsList;
        } else {
            return new ArrayList<LoanEntity>();
        }
    }

    public LoanEntity getLoanById(Long id) {
        Optional<LoanEntity> loanFound = repository.findById(id);

        if(loanFound.isPresent()) {
            return loanFound.get();
        } else {
            System.out.println("No loan record exist for given id");
            return null;
        }

    }

    public LoanEntity createNewLoan(LoanEntity entity) {
        LoanEntity newEntity = new LoanEntity();
        Optional<LoanEntity> loan = repository.findById(entity.getId());
        System.out.println(entity.getNameOfLoan());
        if(loan.isPresent()) {

            return entity;
        } else {
            newEntity.setMaxAmountOfLoan(entity.getMaxAmountOfLoan());
            newEntity.setNameOfLoan(entity.getNameOfLoan());
            for(int i = 0; i < entity.getPaymentsOfLoan().size(); i++) {
                System.out.println("Hi");
                System.out.println((Integer) entity.getPaymentsOfLoan().toArray()[i]);
                newEntity.addToPaymentsLoan((Integer) entity.getPaymentsOfLoan().toArray()[i]);
            }
            entity = repository.save(entity);
            return entity;
        }
    }



    public void deleteLoanById(Long id)
    {
        Optional<LoanEntity> account = repository.findById(id);
        if(account.isPresent()){
            repository.deleteById(id);
        }
    }
}