package com.example.demo.repositories;

import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User createUser(User user, Role role);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    void setStatusUser(String username, int status);

    User updateUser(User user);

    boolean usernameExist(String username);

    boolean emailExist(String email);

    boolean phoneNumberExist(String phoneNumber);

}
