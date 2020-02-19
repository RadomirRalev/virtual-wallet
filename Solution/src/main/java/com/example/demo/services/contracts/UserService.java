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

    boolean isEnabled(String username);

    String getAvailableSum(int userId);

    List<User> searchByUsername(String username, int page);

    List<User> searchByPhoneNumber(String phoneNum, int page);

    List<User> searchByEmail(String email, int page);

    List<User> searchByUsernameAsAdmin(String username, int page);

    List<User> searchByPhoneNumberAsAdmin(String phoneNum, int page);

    List<User> searchByEmailAsAdmin(String email, int page);

    boolean checkIfUserIdExists(int id);
}
