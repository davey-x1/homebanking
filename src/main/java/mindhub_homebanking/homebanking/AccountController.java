package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;

import mindhub_homebanking.homebanking.dto.AccountDTO;
import mindhub_homebanking.homebanking.dto.ClientDTO;
import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

/* ---------------------------------- */

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        List<AccountDTO> listDTO = service.getAllAcounts().stream().map(AccountDTO::new).collect(toList());
        return new ResponseEntity<List<AccountDTO>>(listDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long id) {
        AccountEntity entity = service.getAccountById(id);
        AccountDTO entityDTO = new AccountDTO(entity);
        return new ResponseEntity<AccountDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createOrUpdateAccount(@RequestBody AccountEntity account) {
        System.out.println("Funciona?");
        AccountEntity updated = service.createOrUpdateAccount(account);
        AccountDTO entityDTO = new AccountDTO(updated);
        return new ResponseEntity<AccountDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteAccountById(@PathVariable("id") Long id) {
        service.deleteAccountById(id);
        return HttpStatus.FORBIDDEN;
    }
}
