package com.example.demo.models.card.creditcard;

import org.springframework.stereotype.Component;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class CreditCardMapper {

    public CreditCard validationData(CreditCardDTO creditCardDTO){
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(creditCardDTO.getNumber());
        creditCard.setExpirationDate(creditCardDTO.getExpirationDate());
        creditCard.setSecurityCode(creditCardDTO.getSecurityCode());
        creditCard.setStatus(ENABLE);
        return creditCard;
    }

}
