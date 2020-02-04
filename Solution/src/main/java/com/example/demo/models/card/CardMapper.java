package com.example.demo.models.card;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class CardMapper {

    public CardDetails mapCard(CardRegistrationDTO cardRegistrationDTO) {
        CardDetails cardDetails = new CardDetails();
        cardDetails.setCardNumber(cardRegistrationDTO.getCardNumber());
        cardDetails.setExpirationDate(cardRegistrationDTO.getExpirationDate());
        cardDetails.setSecurityCode(cardRegistrationDTO.getSecurityCode());
        cardDetails.setCardName(cardRegistrationDTO.getCardName());
        cardDetails.setEnabled(ENABLE);
        cardDetails.setCardholderName(cardRegistrationDTO.getCardholderName());
        return cardDetails;
    }
}
