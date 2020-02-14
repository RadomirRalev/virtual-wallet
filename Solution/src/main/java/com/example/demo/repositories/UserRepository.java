package com.example.demo.repositories;

import com.example.demo.models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers(int page);

    User createUser(User user);

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    void setStatusUser(String username, boolean status);

    void setStatusIdentity(String username, boolean status);

    User updateUser(User user);

    User changePassword(User user);

    User updateNames(User user);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    boolean isIdentityConfirm(String username);

    boolean checkIfUserIdExists(int id);

    List<User> searchByUsername(String username);

    List<User> searchByPhoneNumber(String phoneNum);

    List<User> searchByEmail(String email);

}
