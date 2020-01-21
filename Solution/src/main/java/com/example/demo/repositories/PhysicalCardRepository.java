package com.example.demo.repositories;

import com.example.demo.models.card.physical.PhysicalCard;

public interface PhysicalCardRepository {

    PhysicalCard getById(int id);

    PhysicalCard getByNumber(String number);

    PhysicalCard createPhysicalCard(PhysicalCard physicalCard);

    PhysicalCard updatePhysicalCard(PhysicalCard physicalCard);

    void setStatusPhysicalCard(String number, int status);

    boolean isPhysicalCardExist(String number);

}
