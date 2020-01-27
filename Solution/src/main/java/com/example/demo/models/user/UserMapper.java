package com.example.demo.models.user;

import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.demo.constants.SQLQueryConstants.*;
import static com.example.demo.helpers.UserHelper.setOptionalFields;

@Component
public class UserMapper {

    private WalletService walletService;

    @Autowired
    public UserMapper(WalletService walletService) {
        this.walletService = walletService;
    }

    public User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(userRegistrationDTO.getPassword());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPicture(userRegistrationDTO.getFile().getBytes());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        setOptionalFields(user);
        user.setWallet(walletService.createWallet());
        user.setBlocked(false);
        return user;
    }

    public Role createRole(UserRegistrationDTO userRegistrationDTO) {
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
