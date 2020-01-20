package com.example.demo.services;

import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.models.registration.RegistrationDTO;

public interface PhysicalCartService {

    PhysicalCard createPhysicalCard(RegistrationDTO registrationDTO);

    PhysicalCard updatePhysicalCard(PhysicalCard physicalCard);

    PhysicalCard getByNumber(String number);

    void setStatusPhysicalCard(String number, int status);

    boolean isPhysicalCardExist(String number);
}
