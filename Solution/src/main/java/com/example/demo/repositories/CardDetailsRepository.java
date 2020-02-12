package com.example.demo.repositories;

import com.example.demo.models.card.CardDetails;

public interface CardDetailsRepository {

    CardDetails getById(int id);

    CardDetails getByNumber(String number);

    CardDetails createCard(CardDetails cardDetails);

    CardDetails updateCard(CardDetails cardDetails);

    void setCardStatus(String number, boolean status);

    boolean checkIfCardNumberExists(String number);

    boolean checkIfCardIdExists(int cardId);

}
