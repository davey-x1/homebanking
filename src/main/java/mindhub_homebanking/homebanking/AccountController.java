package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;
import mindhub_homebanking.homebanking.repositories.models.AccountEntity;
import mindhub_homebanking.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* ---------------------------------- */

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountEntity>> getAllAccounts() {
        List<AccountEntity> list = service.getAllAcounts();

        return new ResponseEntity<List<AccountEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> getAccountById(@PathVariable("id") Long id) {
        AccountEntity entity = service.getAccountById(id);

        return new ResponseEntity<AccountEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountEntity> createOrUpdateAccount(@RequestBody AccountEntity account) {
        AccountEntity updated = service.createOrUpdateAccount(account);
        return new ResponseEntity<AccountEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteAccountById(@PathVariable("id") Long id) {
        service.deleteAccountById(id);
        return HttpStatus.FORBIDDEN;
    }
}
