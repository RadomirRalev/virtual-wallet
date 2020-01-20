package com.example.demo.repositories;

import com.example.demo.models.card.physical.PhysicalCard;

public interface PhysicalCardRepository {

    PhysicalCard createPhysicalCard(PhysicalCard physicalCard);

    PhysicalCard updatePhysicalCard(PhysicalCard physicalCard);

    PhysicalCard getByNumber(String number);

    void setStatusPhysicalCard(String number, int status);

    boolean isPhysicalCardExist(String number);

}
