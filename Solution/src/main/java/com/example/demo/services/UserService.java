package com.example.demo.services;

import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User createUser(UserRegistrationDTO userRegistrationDTO);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    void setStatusUser(String username, int status);

    User updateUser(User user);

    boolean usernameExist(String username);

    boolean emailExist(String email);

    boolean phoneNumberExist(String phoneNumber);

}
