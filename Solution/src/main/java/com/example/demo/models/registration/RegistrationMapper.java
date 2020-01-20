package com.example.demo.models.registration;

import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.*;

@Component
public class RegistrationMapper {


    public User mapUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setEnabled(ENABLE);
        user.setPassword(registrationDTO.getPassword());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setPicture(registrationDTO.getPicture());
        return user;
    }

    public Role mapRole(RegistrationDTO registrationDTO) {
        Role role = new Role();
        role.setUsername(registrationDTO.getUsername());
        role.setRole(ROLE_USER);
        return role;
    }

//TODO regex logic + debit/credit card !
    public PhysicalCard mapPhysicalCard(RegistrationDTO registrationDTO){
        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setNumber(registrationDTO.getNumber());
        physicalCard.setExpirationDate(registrationDTO.getExpirationDate());
        physicalCard.setSecurityCode(registrationDTO.getSecurityCode());
        physicalCard.setStatus(ENABLE);
        return physicalCard;
    }
}
