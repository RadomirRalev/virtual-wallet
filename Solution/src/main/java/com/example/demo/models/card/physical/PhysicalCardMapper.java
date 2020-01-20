package com.example.demo.models.card.physical;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class PhysicalCardMapper {

    public PhysicalCard validationData(PhysicalCardDTO physicalCardDTO){
        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setNumber(physicalCardDTO.getNumber());
        physicalCard.setExpirationDate(physicalCardDTO.getExpirationDate());
        physicalCard.setSecurityCode(physicalCardDTO.getSecurityCode());
        physicalCard.setStatus(ENABLE);
        return physicalCard;
    }
}

