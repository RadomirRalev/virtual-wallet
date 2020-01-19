package com.example.demo.repositories;

import com.example.demo.models.card.creditcard.CreditCard;

public interface CreditCardRepository {

    CreditCard createCreditCard(CreditCard creditCard);

    CreditCard updateCreditCard(CreditCard creditCard);

    CreditCard getByNumber(String number);

    void setStatusCreditCard(String number, int status);

    boolean creditCardExist(String number);
}
