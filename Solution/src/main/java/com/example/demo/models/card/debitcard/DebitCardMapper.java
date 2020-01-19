package com.example.demo.models.card.debitcard;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class DebitCardMapper {

    public DebitCard validationData(DebitCardDTO debitCardDTO){
        DebitCard debitCard = new DebitCard();
        debitCard.setNumber(debitCardDTO.getNumber());
        debitCard.setExpirationDate(debitCardDTO.getExpirationDate());
        debitCard.setSecurityCode(debitCardDTO.getSecurityCode());
        debitCard.setStatus(ENABLE);
        return debitCard;
    }
}

