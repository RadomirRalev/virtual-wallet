package com.example.demo.models.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

import static com.example.demo.constants.TypesConstants.EMPTY;

public class UserMapper {

    public static User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException {
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPicture(userRegistrationDTO.getFile().getBytes());
        user.setFirstName(EMPTY);
        user.setLastName(EMPTY);
        user.setBlocked(false);
        return user;
    }

    public static User updateProfile(User user, ProfileUpdateDTO profileUpdateDTO) throws IOException {
        user.setPhoneNumber(profileUpdateDTO.getPhoneNumber());
        user.setEmail(profileUpdateDTO.getEmail());
        user.setPicture(profileUpdateDTO.getFile().getBytes());
        return user;
    }

    public static User updateNames(User user, UserNamesDTO userNamesDTO)  {
       user.setFirstName(userNamesDTO.getFirstName());
       user.setLastName(userNamesDTO.getLastName());
        return user;
    }
}
