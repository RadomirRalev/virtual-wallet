package com.example.demo.services;

import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.registration.RegistrationDTO;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User createUser(RegistrationDTO registrationDTO);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    User updateUser(User user, ProfileUpdateDTO profileUpdateDTO);

    void setStatusUser(String username, int status);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

}
