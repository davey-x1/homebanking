package mindhub_homebanking.homebanking.dto;

import mindhub_homebanking.homebanking.repositories.models.CardEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;

import java.time.LocalDate;

public class CardDTO {
    public long id;
    public String cardType;
    public String cardColor;
    public ClientEntity cardHolder;
    public long cardNumber;
    public int cardCvv;
    public LocalDate fromDate;
    public LocalDate thruDate;

    public CardDTO(CardEntity card) {

        this.id = card.getId();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();

        this.cardNumber = card.getNumber();
        this.cardCvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();

    }
}
