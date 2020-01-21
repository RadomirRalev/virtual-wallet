package com.example.demo.models.user;

import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.*;

@Component
public class UserMapper {


    public User createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(ENABLE);
        user.setPassword(userRegistrationDTO.getPassword());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPicture(userRegistrationDTO.getPicture());
        return user;
    }

    public Role mapRole(UserRegistrationDTO userRegistrationDTO) {
        Role role = new Role();
        role.setUsername(userRegistrationDTO.getUsername());
        role.setRole(ROLE_USER);
        return role;
    }

    public User updateProfile(User user, ProfileUpdateDTO profileUpdateDTO) {
        user.setPhoneNumber(profileUpdateDTO.getPhoneNumber());
        user.setEmail(profileUpdateDTO.getEmail());
        user.setPicture(profileUpdateDTO.getPicture());
        return user;
    }
}
