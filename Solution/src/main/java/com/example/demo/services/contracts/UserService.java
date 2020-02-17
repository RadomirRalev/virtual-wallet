package com.example.demo.services.contracts;

import com.example.demo.models.user.*;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> getUsers(int page);

    List<User> getUsersForConfirm();

    User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException;

    User getById(int id);

    User getByUsername(String username);

    User getByPhoneNumber(String phoneNumber);

    User getByEmail(String email);

    User updateUser(User user, ProfileUpdateDTO profileUpdateDTO) throws IOException;

    User changePassword(User user, PasswordUpdateDTO passwordUpdateDTO);

    User updateNames(User user, UserNamesDTO userNamesDTO, String principal);

    void setStatusUser(String username, boolean status);

    void setBlockedStatus(String username, boolean status);

    void setStatusIdentity(String username, boolean status);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    boolean isPhoneNumberExist(String phoneNumber);

    boolean isIdentityConfirm(String username);

    boolean isBlocked(String username);

    boolean isBlocked(int userId);

    boolean isEnabled(String username);

    String getAvailableSum(int userId);

    List<User> searchByUsername(String username);

    List<User> searchByPhoneNumber(String phoneNum);

    List<User> searchByEmail(String email);

    List<User> searchByUsernameAsAdmin(String username);

    List<User> searchByPhoneNumberAsAdmin(String phoneNum);

    List<User> searchByEmailAsAdmin(String email);

    boolean checkIfUserIdExists(int id);
}
