package mindhub_homebanking.homebanking.services;
/* ---------------------------------- */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* ---------------------------------- */

import mindhub_homebanking.homebanking.repositories.models.CardEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.CardRepository;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class CardService {
    @Autowired
    CardRepository repository;
    @Autowired
    ClientRepository clientRepository;

    public List<CardEntity> getAllCards(){
        List<CardEntity> cardList = repository.findAll();

        if(cardList.size() > 0) {
            return cardList;
        } else {
            return new ArrayList<CardEntity>();
        }
    }

    public CardEntity getCardById(Long id) {
        Optional<CardEntity> cardFound = repository.findById(id);

        if(cardFound.isPresent()) {
            return cardFound.get();
        } else {
            System.out.println("No card record exist for given id");
            return null;
        }

    }

    public CardEntity getCardByNumber(long number) {
        Optional<CardEntity> card = repository.findByNumber(number);

        if(card.isPresent()) {
            return card.get();

        } else {
            System.out.println("No card record exist for given id");
            return null;
        }

    }

    public CardEntity createOrUpdateCard(CardEntity entity){

        LocalDate fromFecha = LocalDate.now();
        LocalDate thruFecha = fromFecha.plusYears(3).plusDays(3);

        long smallest = 1000_0000_0000_0000L;
        long biggest =  9999_9999_9999_9999L;
        long randomNumber = ThreadLocalRandom.current().nextLong(smallest, biggest+1);

        Random rnd = new Random();
        int cvvNumber = rnd.nextInt(999);


        Optional<CardEntity> card = repository.findById(entity.getId());
        System.out.println(entity.toString());
        if(card.isPresent()){
            CardEntity newCard = card.get();
            ClientEntity clientEntity = clientRepository.getById(entity.getCardOwner().getId());
            newCard.setCardOwner(newCard.getCardOwner());
            newCard.setCardColor(entity.getCardColor());
            newCard.setCvv(entity.getCvv());
            newCard.setNumber(entity.getNumber());
            newCard.setFromDate(entity.getFromDate());
            newCard.setCardType(entity.getCardType());
            newCard.setThruDate(entity.getThruDate());

            clientEntity.addCardsOwned(newCard);
            clientRepository.save(clientEntity);
            newCard = repository.save(newCard);
            return newCard;

        } else {
            ClientEntity clientEntity = clientRepository.getById(entity.getCardOwner().getId());
            CardEntity newCard = new CardEntity();
            newCard.setThruDate(thruFecha);
            newCard.setFromDate(fromFecha);
            newCard.setCardType(entity.getCardType());
            newCard.setNumber(randomNumber);
            newCard.setCardColor(entity.getCardColor());
            newCard.setCvv(cvvNumber);
            newCard.setCardOwner(entity.getCardOwner());
            clientEntity.addCardsOwned(newCard);
            clientRepository.save(clientEntity);
            newCard = repository.save(newCard);
            return newCard;
        }
    }

    public void deleteCard(Long id) {
        Optional<CardEntity> card = repository.findById(id);
        if(card.isPresent())
        {
            repository.deleteById(id);
        }
    }
}