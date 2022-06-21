package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;

import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoan;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import mindhub_homebanking.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* ---------------------------------- */

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanService service;

    @GetMapping
    public ResponseEntity<List<LoanEntity>> getAllLoans() {
        List<LoanEntity> list = service.getAllLoans();

        return new ResponseEntity<List<LoanEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoanById(@PathVariable("id") Long id) {
        LoanEntity entity = service.getLoanById(id);

        return new ResponseEntity<LoanEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LoanEntity> updatePaymentLoan(@RequestBody LoanEntity loan) {
        LoanEntity updated = service.createNewLoan(loan);
        return new ResponseEntity<LoanEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add/loan")
    public void updatePaymentLoand(@RequestBody ClientLoan loan) {
        System.out.println(loan);
        //ClientLoan updated = service.updateLoan(loan);
        //return new ResponseEntity<ClientEntity>(loan, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteLoanById(@PathVariable("id") Long id) {
        service.deleteLoanById(id);
        return HttpStatus.FORBIDDEN;
    }
}
