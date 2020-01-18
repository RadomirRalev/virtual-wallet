package com.example.demo.repositories;

import com.example.demo.models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User createUser(User user);

    User getByUsername(String name);

    User getByPhoneNumber(String name);

    void setStatusUser(String username,int status);

    User updateUser(User user);

    boolean usernameExist(String name);

    boolean emailExist(String email);

    boolean phoneNumberExist(String email);

}
