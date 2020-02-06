package com.example.demo.models.card;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class CardMapper {

    private UserService userService;

    @Autowired
    public CardMapper(UserService userService) {
        this.userService = userService;
    }

    public CardDetails mapCard(CardRegistrationDTO cardRegistrationDTO) {
        CardDetails cardDetails = new CardDetails();
        cardDetails.setCardNumber(cardRegistrationDTO.getCardNumber());
        cardDetails.setExpirationDate(cardRegistrationDTO.getExpirationDate());
        cardDetails.setSecurityCode(cardRegistrationDTO.getSecurityCode());
        cardDetails.setEnabled(ENABLE);
        cardDetails.setCardholderName(cardRegistrationDTO.getCardholderName());
        cardDetails.setUser(userService.getById(cardRegistrationDTO.getUser_id()));
        return cardDetails;
    }
}
