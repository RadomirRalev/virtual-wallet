package com.example.demo.repositories;

import com.example.demo.models.card.virtual.VirtualCard;

public interface VirtualCardRepository {

    VirtualCard createCreditCard(VirtualCard virtualCard);

    VirtualCard updateCreditCard(VirtualCard virtualCard);

    VirtualCard getByNumber(String number);

    void setStatusCreditCard(String number, int status);

    boolean creditCardExist(String number);
}
