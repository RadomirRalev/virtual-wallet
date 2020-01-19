package com.example.demo.models.user;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.*;

@Component
public class UserMapper {

    //TODO logic for optional credit/debit validation !


    public User setUser(UserRegistrationDTO userRegistrationDTO) {
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

    public Role setRole(UserRegistrationDTO userRegistrationDTO) {
        Role role = new Role();
        role.setUsername(userRegistrationDTO.getUsername());
        role.setRole(ROLE_USER);
        return role;
    }


}
