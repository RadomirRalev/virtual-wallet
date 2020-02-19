package com.example.demo.repositories.contracts;

import com.example.demo.models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers(int page);

    List<User> getUsersForConfirm();

    User createUser(User user);

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    void setStatusUser(String username, boolean status);

    void setBlockedStatus(String username, boolean status);

    void setStatusIdentity(String username, boolean status);

    User updateUser(User user);

    User changePassword(User user);

    User updateNames(User user);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    boolean isIdentityConfirm(String username);

    boolean isBlocked(String username);

    boolean isEnabled(String username);

    boolean checkIfUserIdExists(int id);

    List<User> searchByUsername(String username, int page);

    List<User> searchByPhoneNumber(String phoneNum, int page);

    List<User> searchByEmail(String email, int page);

    List<User> searchByUsernameAsAdmin(String username, int page);

    List<User> searchByPhoneNumberAsAdmin(String phoneNum, int page);

    List<User> searchByEmailAsAdmin(String email, int page);

}
