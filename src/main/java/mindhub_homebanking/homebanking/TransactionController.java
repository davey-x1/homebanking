package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mindhub_homebanking.homebanking.dto.ClientDTO;
import mindhub_homebanking.homebanking.dto.LoanDTO;
import mindhub_homebanking.homebanking.dto.TransactionDTO;
import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.TransactionEntity;
import mindhub_homebanking.homebanking.services.ClientService;
import mindhub_homebanking.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static java.util.stream.Collectors.toList;

/* ---------------------------------- */

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService service;

    @PostMapping("/create")
    public ResponseEntity<List<TransactionDTO>> createOrUpdateTransaction(
            @RequestParam int monto,
            @RequestParam String description,
            @RequestParam String cuentaOrigen,
            @RequestParam String cuentaDestino) {
        List<TransactionDTO> listDTO = service.createTransaction(monto, description, cuentaOrigen, cuentaDestino).stream().map(TransactionDTO::new).collect(toList());
        return new ResponseEntity<>(listDTO, new HttpHeaders(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpStatus deleteTransactionById(@PathVariable("id") Long id) {

        return HttpStatus.FORBIDDEN;
    }
}
