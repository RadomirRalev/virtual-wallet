package com.example.demo.services;

import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.models.card.physical.PhysicalCardMapper;
import com.example.demo.models.registration.RegistrationDTO;
import com.example.demo.models.registration.RegistrationMapper;
import com.example.demo.repositories.PhysicalCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class PhysicalCartServiceImpl implements PhysicalCartService {

    private PhysicalCardRepository physicalCardRepository;
    private PhysicalCardMapper physicalCardMapper;
    private RegistrationMapper registrationMapper;

    @Autowired
    public PhysicalCartServiceImpl(PhysicalCardRepository physicalCardRepository, PhysicalCardMapper physicalCardMapper,
                                   RegistrationMapper registrationMapper) {
        this.physicalCardRepository = physicalCardRepository;
        this.physicalCardMapper = physicalCardMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public PhysicalCard createPhysicalCard(RegistrationDTO registrationDTO) {
        PhysicalCard physicalCard = registrationMapper.mapPhysicalCard(registrationDTO);
        return physicalCardRepository.createPhysicalCard(physicalCard);
    }
//TODO method w/o registrationDTO
    @Override
    public PhysicalCard updatePhysicalCard(PhysicalCard physicalCard) {
        throw new NotImplementedException();
    }

    @Override
    public PhysicalCard getByNumber(String number) {
        return physicalCardRepository.getByNumber(number);
    }

    @Override
    public void setStatusPhysicalCard(String number, int status) {
        physicalCardRepository.setStatusPhysicalCard(number, status);
    }

    @Override
    public boolean isPhysicalCardExist(String number) {
        return physicalCardRepository.isPhysicalCardExist(number);
    }
}
