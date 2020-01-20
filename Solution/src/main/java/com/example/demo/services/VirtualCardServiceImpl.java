package com.example.demo.services;

import com.example.demo.models.card.virtual.VirtualCard;
import com.example.demo.models.card.virtual.VirtualCardDTO;
import com.example.demo.models.card.virtual.VirtualCardMapper;
import com.example.demo.repositories.VirtualCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class VirtualCardServiceImpl implements VirtualCardService {

    private VirtualCardRepository virtualCardRepository;
    private VirtualCardMapper virtualCardMapper;

    @Autowired
    public VirtualCardServiceImpl(VirtualCardRepository virtualCardRepository, VirtualCardMapper virtualCardMapper) {
        this.virtualCardRepository = virtualCardRepository;
        this.virtualCardMapper = virtualCardMapper;
    }

    @Override
    public VirtualCard createVirtualCard(VirtualCardDTO virtualCardDTO) {
        return virtualCardMapper.validationData(virtualCardDTO);
    }

    //TODO impelemntation !
    @Override
    public VirtualCard updateVirtualCard(VirtualCard virtualCard) {
        throw new NotImplementedException();
    }

    @Override
    public VirtualCard getByNumber(String number) {
        return virtualCardRepository.getByNumber(number);
    }

    @Override
    public void setStatusVirtualCard(String number, int status) {
        virtualCardRepository.setStatusCreditCard(number, status);
    }

    @Override
    public boolean isVirtualCardExist(String number) {
        return virtualCardRepository.creditCardExist(number);
    }
}
