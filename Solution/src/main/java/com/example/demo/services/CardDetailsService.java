package com.example.demo.services;

import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;

public interface CardDetailsService {

    CardDetails getById(int id);

    CardDetails getByNumber(String number);

    CardDetails createCard(CardRegistrationDTO cardRegistrationDTO, String username);

    CardDetails updateCard(CardRegistrationDTO cardRegistrationDTO);

    void setCardStatus(String number, boolean status);

    boolean isCardExist(String number);
}
