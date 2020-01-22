package com.example.demo.services;

import com.example.demo.models.card.CardDTO;
import com.example.demo.models.card.CardDetails;

public interface CardDetailsService {

    CardDetails getById(int id);

    CardDetails getByNumber(String number);

    CardDetails createCard(CardDTO cardDTO, String username);

    CardDetails updateCard(CardDTO cardDTO);

    void setCardStatus(String number, int status);

    boolean isCardExist(String number);
}
