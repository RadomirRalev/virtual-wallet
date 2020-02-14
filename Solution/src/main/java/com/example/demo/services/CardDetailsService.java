package com.example.demo.services;

import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardUpdateDTO;

public interface CardDetailsService {

    CardDetails getById(int id);

    CardDetails getByNumber(String number);

    CardDetails createCard(CardRegistrationDTO cardRegistrationDTO, String username);

    CardDetails updateCard(CardUpdateDTO cardUpdateDTO, int updatedCardId, String username);

    void setCardStatus(String number, boolean status);

    boolean isCardExist(String number);

    boolean isUserIsOwner(int cardId, int userId);
}
