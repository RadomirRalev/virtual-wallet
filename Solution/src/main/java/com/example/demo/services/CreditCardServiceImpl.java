package com.example.demo.services;

import com.example.demo.models.card.creditcard.CreditCard;
import com.example.demo.models.card.creditcard.CreditCardDTO;
import com.example.demo.models.card.creditcard.CreditCardMapper;
import com.example.demo.models.card.debitcard.DebitCardMapper;
import com.example.demo.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepository creditCardRepository;
    private CreditCardMapper creditCardMapper;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, CreditCardMapper creditCardMapper) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardMapper = creditCardMapper;
    }

    @Override
    public CreditCard createCreditCard(CreditCardDTO creditCardDTO) {
        return creditCardMapper.validationData(creditCardDTO);
    }

    //TODO impelemntation !
    @Override
    public CreditCard updateCreditCard(CreditCard creditCard) {
        throw new NotImplementedException();
    }

    @Override
    public CreditCard getByNumber(String number) {
        return creditCardRepository.getByNumber(number);
    }

    @Override
    public void setStatusCreditCard(String number, int status) {
        creditCardRepository.setStatusCreditCard(number, status);
    }

    @Override
    public boolean creditCardExist(String number) {
        return creditCardRepository.creditCardExist(number);
    }
}
