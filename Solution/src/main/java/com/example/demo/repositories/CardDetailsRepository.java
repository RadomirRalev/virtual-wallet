package com.example.demo.repositories;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.user.User;

public interface CardDetailsRepository {

    CardDetails getById(int id);

    CardDetails getByNumber(String number);

    CardDetails createCard(CardDetails cardDetails, User user);

    CardDetails updateCard(CardDetails cardDetails);

    void setCardStatus(String number, int status);

    boolean isCardExist(String number);

}
