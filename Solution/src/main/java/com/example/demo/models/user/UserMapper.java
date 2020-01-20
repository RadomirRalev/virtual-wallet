package com.example.demo.models.user;

import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User updateProfile(User user, ProfileUpdateDTO profileUpdateDTO) {
        user.setPhoneNumber(profileUpdateDTO.getPhoneNumber());
        user.setEmail(profileUpdateDTO.getEmail());
        user.setPicture(profileUpdateDTO.getPicture());
        return user;
    }
}
