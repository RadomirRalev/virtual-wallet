package com.example.demo.services;

import com.example.demo.models.card.creditcard.CreditCard;
import com.example.demo.models.card.creditcard.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {

    CreditCard createCreditCard(CreditCardDTO creditCardDTO);

    CreditCard updateCreditCard(CreditCard creditCard);

    CreditCard getByNumber(String number);

    void setStatusCreditCard(String number, int status);

    boolean creditCardExist(String number);
}
