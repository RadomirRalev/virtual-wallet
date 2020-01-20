package com.example.demo.models.card.virtual;

import org.springframework.stereotype.Component;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class VirtualCardMapper {

    public VirtualCard validationData(VirtualCardDTO virtualCardDTO){
        VirtualCard virtualCard = new VirtualCard();
        virtualCard.setNumber(virtualCardDTO.getNumber());
        virtualCard.setExpirationDate(virtualCardDTO.getExpirationDate());
        virtualCard.setSecurityCode(virtualCardDTO.getSecurityCode());
        virtualCard.setStatus(ENABLE);
        return virtualCard;
    }

}
