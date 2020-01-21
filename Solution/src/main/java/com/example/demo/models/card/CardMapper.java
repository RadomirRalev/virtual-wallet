package com.example.demo.models.card;

import com.example.demo.models.card.physical.PhysicalCard;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;
@Component

public class CardMapper {

    //TODO regex logic + debit/credit card !
    public PhysicalCard mapPhysicalCard(CardDTO cardDTO){
        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setNumber(cardDTO.getNumber());
        physicalCard.setExpirationDate(cardDTO.getExpirationDate());
        physicalCard.setSecurityCode(cardDTO.getSecurityCode());
        physicalCard.setStatus(ENABLE);
        return physicalCard;
    }
}
