package com.example.demo.services;

import com.example.demo.models.card.debitcard.DebitCard;
import com.example.demo.models.card.debitcard.DebitCardDTO;
import com.example.demo.models.card.debitcard.DebitCardMapper;
import com.example.demo.repositories.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class DebitCartServiceImpl implements DebitCartService {

    private DebitCardRepository debitCardRepository;
    private DebitCardMapper debitCardMapper;

    @Autowired
    public DebitCartServiceImpl(DebitCardRepository debitCardRepository, DebitCardMapper debitCardMapper) {
        this.debitCardRepository = debitCardRepository;
        this.debitCardMapper = debitCardMapper;
    }

    @Override
    public DebitCard createDebitCard(DebitCardDTO debitCardDTO) {
        return debitCardMapper.validationData(debitCardDTO);
    }
    //TODO impelemntation !

    @Override
    public DebitCard updateDebitCard(DebitCard debitCard) {
        throw new NotImplementedException();
    }

    @Override
    public DebitCard getByNumber(String number) {
        return debitCardRepository.getByNumber(number);
    }

    @Override
    public void setStatusDebitCard(String number, int status) {
        debitCardRepository.setStatusDebitCard(number, status);
    }

    @Override
    public boolean debitCardExist(String number) {
        return debitCardRepository.debitCardExist(number);
    }
}
