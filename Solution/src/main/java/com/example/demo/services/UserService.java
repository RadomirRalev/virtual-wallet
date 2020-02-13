package com.example.demo.services;

import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> getUsers(int page);

    User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    User updateUser(User user, ProfileUpdateDTO profileUpdateDTO) throws IOException;

    User changePassword (User user, PasswordUpdateDTO passwordUpdateDTO);

    void setStatusUser(String username, boolean status);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    String getAvailableSum(int userId);

    List<User> searchByUsername(String username);

    List<User> searchByPhoneNumber(String phoneNum);

    List<User> searchByEmail(String email);

}
