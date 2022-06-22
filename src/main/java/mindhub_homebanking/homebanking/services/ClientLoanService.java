package mindhub_homebanking.homebanking.services;

/* ---------------------------------- */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mindhub_homebanking.homebanking.repositories.ClientLoanRepository;
import mindhub_homebanking.homebanking.repositories.LoanRepository;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class ClientLoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<ClientLoanEntity> getAllClientLoans() {
        List<ClientLoanEntity> accountsList = clientLoanRepository.findAll();

        if(accountsList.size() > 0) {
            return accountsList;
        } else {
            return new ArrayList<ClientLoanEntity>();
        }
    }
    public ClientLoanEntity createOrUpdateClientLoan(ClientLoanEntity entity){
        System.out.println(entity.getAmountOfLoan());
        Optional<ClientLoanEntity> clientLoan = clientLoanRepository.findById(entity.getId());
        if(clientLoan.isPresent()){
            ClientLoanEntity entityUsed = clientLoan.get();
            ClientEntity clientEntity = clientRepository.getById(entityUsed.getOwnerOfLoan().getId());
            LoanEntity loanEntity = loanRepository.getById(entity.getLoanEntity().getId());
            entityUsed.setPaymentOfLoans(entityUsed.getPaymentOfLoans());
            entityUsed.setAmountOfLoan(entityUsed.getAmountOfLoan());
            entityUsed.setOwnerOfLoan(clientEntity);
            entityUsed.setLoanEntity(loanEntity);
            entityUsed.setLoanId((int) loanEntity.getId());
            entityUsed.setNameOfLoan(loanEntity.getNameOfLoan());
            clientEntity.setLoansOwned(entityUsed);
            loanEntity.setLoansOwned(entityUsed);
            clientRepository.save(clientEntity);
            loanRepository.save(loanEntity);
            entityUsed = clientLoanRepository.save(entityUsed);
            return entityUsed;
        } else {
            ClientLoanEntity entityUsed = new ClientLoanEntity();

            ClientEntity clientEntity = clientRepository.getById(entity.getOwnerOfLoan().getId());
            LoanEntity loanEntity = loanRepository.getById(entity.getLoanEntity().getId());
            entityUsed.setPaymentOfLoans(entity.getPaymentOfLoans());
            entityUsed.setAmountOfLoan(entity.getAmountOfLoan());
            System.out.println("Before owner ");
            entityUsed.setOwnerOfLoan(clientEntity);

            entityUsed.setLoanEntity(loanEntity);
            entityUsed.setLoanId((int) loanEntity.getId());
            entityUsed.setNameOfLoan(loanEntity.getNameOfLoan());

            clientEntity.setLoansOwned(entityUsed);
            loanEntity.setLoansOwned(entityUsed);
            clientRepository.save(clientEntity);
            loanRepository.save(loanEntity);
            entityUsed = clientLoanRepository.save(entityUsed);
            System.out.println("After owner 5");
            return entityUsed;
        }
    }

}