package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.CardMapper;
import com.example.demo.models.card.CardDTO;
import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.repositories.PhysicalCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.example.demo.constants.ExceptionConstants.CARD_WITH_NUMBER_EXISTS;
import static com.example.demo.constants.ExceptionConstants.CARD_WITH_NUMBER_NOT_EXISTS;

@Service
public class PhysicalCardServiceImpl implements PhysicalCardService {

    private PhysicalCardRepository physicalCardRepository;
    private CardMapper cardMapper;

    @Autowired
    public PhysicalCardServiceImpl(PhysicalCardRepository physicalCardRepository, CardMapper cardMapper) {
        this.physicalCardRepository = physicalCardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public PhysicalCard getById(int id) {
        return physicalCardRepository.getById(id);
    }

    @Override
    public PhysicalCard getByNumber(String number)
    {
        return physicalCardRepository.getByNumber(number);
    }

    @Override
    public PhysicalCard createPhysicalCard(CardDTO cardDTO) {
        if (isPhysicalCardExist(cardDTO.getNumber())){
            throw new DuplicateEntityException(CARD_WITH_NUMBER_EXISTS, cardDTO.getNumber());
        }
        PhysicalCard physicalCard = cardMapper.mapPhysicalCard(cardDTO);
        return physicalCardRepository.createPhysicalCard(physicalCard);
    }

    @Override
    public PhysicalCard updatePhysicalCard(CardDTO cardDTO) {
        throw new NotImplementedException();
    }

    @Override
    public void setStatusPhysicalCard(String number, int status) {
        if(!isPhysicalCardExist(number)){
            throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS,number);
        }
        physicalCardRepository.setStatusPhysicalCard(number, status);
    }

    @Override
    public boolean isPhysicalCardExist(String number) {
        return physicalCardRepository.isPhysicalCardExist(number);
    }
}
