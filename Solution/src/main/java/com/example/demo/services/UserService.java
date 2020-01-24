package com.example.demo.services;

import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User createUser(UserRegistrationDTO userRegistrationDTO);

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    User updateUser(User user, ProfileUpdateDTO profileUpdateDTO);

    User changePassword (User user, PasswordUpdateDTO passwordUpdateDTO);

    void setStatusUser(String username, int status);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    List<Integer> getPages();

}
