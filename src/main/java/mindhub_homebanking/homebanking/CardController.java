package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;

import mindhub_homebanking.homebanking.dto.CardDTO;
import mindhub_homebanking.homebanking.repositories.models.CardEntity;
import mindhub_homebanking.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* ---------------------------------- */

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService service;

    @GetMapping
    public ResponseEntity<List<CardEntity>> getAllCards() {
        List<CardEntity> list = service.getAllCards();
        return new ResponseEntity<List<CardEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> getCardById(@PathVariable("id") Long id) {
        CardEntity entity = service.getCardById(id);
        return new ResponseEntity<CardEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardDTO> getCardByNumber(@PathVariable("cardNumber") long cardNumber) {
        CardEntity entity = service.getCardByNumber(cardNumber);
        CardDTO entityDTO = new CardDTO(entity);
        return new ResponseEntity<CardDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CardEntity> createOrUpdateCard(@RequestBody CardEntity card) {
        CardEntity updated = service.createOrUpdateCard(card);
        return new ResponseEntity<CardEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteCardById(@PathVariable("id") Long id) {
        service.deleteCard(id);
        return HttpStatus.FORBIDDEN;
    }
}
