package com.example.demo.repositories;

import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import org.hibernate.query.Query;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User createUser(User user, Role role, Wallet wallet);

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    void setStatusUser(String username, boolean status);

    User updateUser(User user);

    User changePassword(User user);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    List<Integer> getPages();

    List<User> getUsersPaginatedHibernate(Integer page);

    boolean doesUserExist(int id);
}
