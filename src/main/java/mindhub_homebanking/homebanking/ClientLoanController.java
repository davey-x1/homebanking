package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;

import mindhub_homebanking.homebanking.dto.CardDTO;
import mindhub_homebanking.homebanking.dto.ClientLoanDTO;
import mindhub_homebanking.homebanking.dto.LoanDTO;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import mindhub_homebanking.homebanking.services.ClientLoanService;
import mindhub_homebanking.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

/* ---------------------------------- */

@RestController
@RequestMapping("/cloans")
public class ClientLoanController {
    @Autowired
    ClientLoanService service;

    @GetMapping()
    public ResponseEntity<List<ClientLoanDTO>> getAllClientLoans() {
        List<ClientLoanDTO> listDTO = service.getAllClientLoans().stream().map(ClientLoanDTO::new).collect(toList());
        return new ResponseEntity<List<ClientLoanDTO>>(listDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ClientLoanEntity> createOrUpdateClientLoans(@RequestBody ClientLoanEntity loan) {
        ClientLoanEntity updated = service.createOrUpdateClientLoan(loan);
        ClientLoanDTO entityDTO = new ClientLoanDTO(updated);
        System.out.println("After owner 6");
        return new ResponseEntity<ClientLoanEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }
}
