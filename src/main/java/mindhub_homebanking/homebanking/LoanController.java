package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;

import mindhub_homebanking.homebanking.dto.CardDTO;
import mindhub_homebanking.homebanking.dto.ClientLoanDTO;
import mindhub_homebanking.homebanking.dto.LoanDTO;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import mindhub_homebanking.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

/* ---------------------------------- */

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanService service;
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> listDTO = service.getAllLoans().stream().map(LoanDTO::new).collect(toList());
        return new ResponseEntity<List<LoanDTO>>(listDTO, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable("id") Long id) {
        LoanEntity entity = service.getLoanById(id);
        LoanDTO entityDTO = new LoanDTO(entity);
        return new ResponseEntity<LoanDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LoanDTO> updatePaymentLoan(@RequestBody LoanEntity loan) {
        LoanEntity updated = service.createNewLoan(loan);
        LoanDTO entityDTO = new LoanDTO(updated);
        return new ResponseEntity<LoanDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteLoanById(@PathVariable("id") Long id) {
        service.deleteLoanById(id);
        return HttpStatus.FORBIDDEN;
    }
}
