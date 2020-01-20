package com.example.demo.services;

import com.example.demo.models.card.virtual.VirtualCard;
import com.example.demo.models.card.virtual.VirtualCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface VirtualCardService {

    VirtualCard createVirtualCard(VirtualCardDTO virtualCardDTO);

    VirtualCard updateVirtualCard(VirtualCard virtualCard);

    VirtualCard getByNumber(String number);

    void setStatusVirtualCard(String number, int status);

    boolean isVirtualCardExist(String number);
}
