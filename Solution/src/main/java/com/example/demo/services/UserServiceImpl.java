package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserMapper;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.example.demo.constants.ExceptionConstants.*;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private DebitCartService debitCartService;
    private CreditCardService creditCardService;
    private UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, DebitCartService debitCartService,
                           CreditCardService creditCardService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.debitCartService = debitCartService;
        this.creditCardService = creditCardService;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {


        if (usernameExist(userRegistrationDTO.getUsername())) {
            throw new DuplicateEntityException(USER_USERNAME_EXISTS, userRegistrationDTO.getUsername());
        }

        if (emailExist(userRegistrationDTO.getEmail())) {
            throw new DuplicateEntityException(USER_EMAIL_EXISTS, userRegistrationDTO.getEmail());
        }

        if (phoneNumberExist(userRegistrationDTO.getPhoneNumber())) {
            throw new DuplicateEntityException(USER_PHONE_EXISTS, userRegistrationDTO.getPhoneNumber());
        }

        if (creditCardService.creditCardExist(userRegistrationDTO.getCreditCardNumber())) {
            throw new DuplicateEntityException(CREDIT_CARD_EXISTS, userRegistrationDTO.getCreditCardNumber());
        }

        if (debitCartService.debitCardExist(userRegistrationDTO.getDebitCardNumber())) {
            throw new DuplicateEntityException(DEBIT_CARD_EXISTS, userRegistrationDTO.getDebitCardNumber());
        }
        //TODO logic for optional cards !

        return userMapper.validationData(userRegistrationDTO);

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
    public void setStatusUser(String username, int status) {
        userRepository.setStatusUser(username, status);
    }

    @Override
    public User updateUser(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean usernameExist(String username) {
        return userRepository.usernameExist(username);
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.emailExist(email);
    }

    @Override
    public boolean phoneNumberExist(String phoneNumber) {
        return userRepository.phoneNumberExist(phoneNumber);
    }
}
