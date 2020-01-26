package com.example.demo.models.card;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class CardMapper {

    public CardDetails mapCard(CardDTO cardDTO) {
        CardDetails cardDetails = new CardDetails();
        cardDetails.setCardNumber(cardDTO.getCardNumber());
        cardDetails.setExpirationDate(cardDTO.getExpirationDate());
        cardDetails.setSecurityCode(cardDTO.getSecurityCode());
        cardDetails.setStatus(ENABLE);
        cardDetails.setCardholderName(cardDTO.getCardholderName());
        return cardDetails;
    }
}
