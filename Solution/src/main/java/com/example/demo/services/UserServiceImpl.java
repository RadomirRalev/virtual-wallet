package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.user.*;
import com.example.demo.models.user.UserMapper;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.constants.ExceptionConstants.*;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public List<Integer> getPages() {
        return userRepository.getPages();
    }

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {

        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirmation())){
            throw new InvalidPasswordException(PASSWORD_DO_NOT_MATCH);
        }

        if (isUsernameExist(userRegistrationDTO.getUsername())) {
            throw new DuplicateEntityException(USER_USERNAME_EXISTS, userRegistrationDTO.getUsername());
        }

        if (isEmailExist(userRegistrationDTO.getEmail())) {
            throw new DuplicateEntityException(USER_EMAIL_EXISTS, userRegistrationDTO.getEmail());
        }

        if (isPhoneNumberExist(userRegistrationDTO.getPhoneNumber())) {
            throw new DuplicateEntityException(USER_PHONE_EXISTS, userRegistrationDTO.getPhoneNumber());
        }

        User user = userMapper.createUser(userRegistrationDTO);
        Role role = userMapper.createRole(userRegistrationDTO);

        return userRepository.createUser(user, role);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.getByPhoneNumber(phoneNumber);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User updateUser(User user, ProfileUpdateDTO profileUpdateDTO) {
        if (!user.getEmail().equals(profileUpdateDTO.getEmail())
                && isEmailExist(profileUpdateDTO.getEmail())) {
            throw new DuplicateEntityException(
                    String.format(EMAIL_ALREADY_REGISTERED, profileUpdateDTO.getEmail()));
        }
        if (!user.getPhoneNumber().equals(profileUpdateDTO.getPhoneNumber())
                && isPhoneNumberExist(profileUpdateDTO.getPhoneNumber())) {
            throw new DuplicateEntityException(
                    String.format(PHONE_NUMBER_ALREADY_REGISTERED, profileUpdateDTO.getPhoneNumber()));
        }
        User userToUpdate = userMapper.updateProfile(user, profileUpdateDTO);
        return userRepository.updateUser(userToUpdate);
    }

    @Override
    public User changePassword(User user, PasswordUpdateDTO passwordUpdateDTO) {
        if (!user.getPassword().equals(passwordUpdateDTO.getOldPassword())) {
            throw new InvalidPasswordException(INVALID_OLD_PASSWORD);
        }
        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getNewPasswordConfirmation())) {
            throw new InvalidPasswordException(PASSWORD_DO_NOT_MATCH);
        }
        user.setPassword(passwordUpdateDTO.getNewPassword());
        return userRepository.changePassword(user);
    }

    @Override
    public void setStatusUser(String username, int status) {
        if(!isUsernameExist(username)){
            throw new EntityNotFoundException(USER_USERNAME_NOT_FOUND,username);
        }
        userRepository.setStatusUser(username, status);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.isUsernameExist(username);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.isEmailExist(email);
    }

    @Override
    public boolean isPhoneNumberExist(String phoneNumber) {
        return userRepository.isPhoneNumberExist(phoneNumber);
    }
}
