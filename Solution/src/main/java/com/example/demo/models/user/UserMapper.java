package com.example.demo.models.user;

import com.example.demo.models.wallet.Wallet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.demo.constants.SQLQueryConstants.*;
import static com.example.demo.helpers.UserHelper.setOptionalFields;

@Component
public class UserMapper {
    private BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPicture(userRegistrationDTO.getFile().getBytes());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        setOptionalFields(user);
        user.setBlocked(false);
        return user;
    }

    public Role createRole(UserRegistrationDTO userRegistrationDTO) {
        Role role = new Role();
        role.setUsername(userRegistrationDTO.getUsername());
        role.setRole(ROLE_USER);
        return role;
    }

    public Wallet registerWallet (){
        Wallet wallet = new Wallet();
        return wallet;
    }

    public User updateProfile(User user, ProfileUpdateDTO profileUpdateDTO) throws IOException {
        user.setPhoneNumber(profileUpdateDTO.getPhoneNumber());
        user.setEmail(profileUpdateDTO.getEmail());
        user.setPicture(profileUpdateDTO.getFile().getBytes());
        return user;
    }
}
