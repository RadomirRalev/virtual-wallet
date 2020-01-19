package com.example.demo.services;

import com.example.demo.models.card.debitcard.DebitCard;
import com.example.demo.models.card.debitcard.DebitCardDTO;
import com.example.demo.repositories.DebitCardRepository;

public interface DebitCartService {

    DebitCard createDebitCard(DebitCardDTO debitCardDTO);

    DebitCard updateDebitCard(DebitCard debitCard);

    DebitCard getByNumber(String number);

    void setStatusDebitCard(String number, int status);

    boolean debitCardExist(String number);
}
