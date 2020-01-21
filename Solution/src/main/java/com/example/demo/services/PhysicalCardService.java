package com.example.demo.services;

import com.example.demo.models.card.CardDTO;
import com.example.demo.models.card.physical.PhysicalCard;

public interface PhysicalCardService {

    PhysicalCard getById(int id);

    PhysicalCard getByNumber(String number);

    PhysicalCard createPhysicalCard(CardDTO cardDTO);

    PhysicalCard updatePhysicalCard(CardDTO cardDTO);

    void setStatusPhysicalCard(String number, int status);

    boolean isPhysicalCardExist(String number);
}
