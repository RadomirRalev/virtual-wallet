package com.example.demo.models.user;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Component
public class UserMapper {
//TODO logic for optional credit/debit validation !


    public User validationData(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
//        user.setCreditCard(userRegistrationDTO.getCreditCard());
//        user.setDebitCard(userRegistrationDTO.getDebitCard());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(ENABLE);
        user.setPassword(userRegistrationDTO.getPassword());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPicture(userRegistrationDTO.getPicture());
        return user;
    }
}
