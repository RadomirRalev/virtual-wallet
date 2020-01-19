package com.example.demo.repositories;

import com.example.demo.models.card.debitcard.DebitCard;

public interface DebitCardRepository {

    DebitCard createDebitCard(DebitCard debitCard);

    DebitCard updateDebitCard(DebitCard debitCard);

    DebitCard getByNumber(String number);

    void setStatusDebitCard(String number, int status);

    boolean debitCardExist(String number);

}
