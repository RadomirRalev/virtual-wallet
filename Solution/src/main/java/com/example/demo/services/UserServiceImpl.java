package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.models.card.physical.PhysicalCard;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import com.example.demo.models.registration.RegistrationMapper;
import com.example.demo.models.registration.RegistrationDTO;
import com.example.demo.models.user.UserMapper;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.helpers.RegistrationChecker.*;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PhysicalCartService physicalCartService;
    private VirtualCardService virtualCardService;
    private RegistrationMapper registrationMapper;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PhysicalCartService physicalCartService,
                           VirtualCardService virtualCardService, RegistrationMapper registrationMapper,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.physicalCartService = physicalCartService;
        this.virtualCardService = virtualCardService;
        this.registrationMapper = registrationMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User createUser(RegistrationDTO registrationDTO) {

        if (isUsernameExist(registrationDTO.getUsername())) {
            throw new DuplicateEntityException(USER_USERNAME_EXISTS, registrationDTO.getUsername());
        }

        if (isEmailExist(registrationDTO.getEmail())) {
            throw new DuplicateEntityException(USER_EMAIL_EXISTS, registrationDTO.getEmail());
        }

        if (isPhoneNumberExist(registrationDTO.getPhoneNumber())) {
            throw new DuplicateEntityException(USER_PHONE_EXISTS, registrationDTO.getPhoneNumber());
        }

        if (!areCardFieldEmpty(registrationDTO) && !areCardFieldNotEmpty(registrationDTO)) {
            throw new InvalidOptionalFieldParameter(FILL_ALL_FIELDS);
        }

        User user = registrationMapper.mapUser(registrationDTO);
        Role role = registrationMapper.mapRole(registrationDTO);

        if (areCardFieldNotEmpty(registrationDTO)) {
            PhysicalCard physicalCard = physicalCartService.createPhysicalCard(registrationDTO);
            user.setPhysicalCard(physicalCard);
        }
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
    public void setStatusUser(String username, int status) {
        userRepository.setStatusUser(username, status);
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
