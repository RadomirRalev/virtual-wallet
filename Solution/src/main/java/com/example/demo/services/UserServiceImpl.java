package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.role.Role;
import com.example.demo.models.role.RoleMapper;
import com.example.demo.models.user.*;
import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.models.verificationToken.VerificationTokenMapper;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletMapper;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WalletRepository;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.FormatConstants.df2;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private WalletRepository walletRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    private VerificationTokenMapper verificationTokenMapper;
    private EmailSenderServiceImpl emailSenderService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           RoleRepository roleRepository, WalletRepository walletRepository, VerificationTokenService verificationTokenService,
                           VerificationTokenMapper verificationTokenMapper, EmailSenderServiceImpl emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.walletRepository = walletRepository;
        this.verificationTokenService = verificationTokenService;
        this.verificationTokenMapper = verificationTokenMapper;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<User> getUsers(int page) {
        return userRepository.getUsers(page);
    }

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) throws IOException {

        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirmation())) {
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

        //TODO change mapper methods names ?
        User user = UserMapper.createUser(userRegistrationDTO);

        User createdUser = userRepository.createUser(user);

        Role role = RoleMapper.createRole(createdUser);
        roleRepository.createRole(role);

        Wallet wallet = WalletMapper.createDefaultWallet(createdUser);
        walletRepository.createWallet(wallet);

        VerificationToken verificationToken = verificationTokenMapper.createVerificationToken(createdUser.getId());
        verificationTokenService.create(verificationToken);
        SimpleMailMessage emailMessage =
                verificationTokenMapper.createEmail(user.getEmail(),verificationToken.getToken());
        emailSenderService.sendEmail(emailMessage);

        return createdUser;
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
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
    public User updateUser(User user, ProfileUpdateDTO profileUpdateDTO) throws IOException {
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
        User userToUpdate = UserMapper.updateProfile(user, profileUpdateDTO);
        return userRepository.updateUser(userToUpdate);
    }

    @Override
    public User changePassword(User user, PasswordUpdateDTO passwordUpdateDTO) {

        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getNewPasswordConfirmation())) {
            throw new InvalidPasswordException(PASSWORD_DO_NOT_MATCH);
        }

        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException(INVALID_CURRENT_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        return userRepository.changePassword(user);
    }

    @Override
    public void setStatusUser(String username, boolean status) {
        if (!isUsernameExist(username)) {
            throw new EntityNotFoundException(USER_USERNAME_NOT_FOUND, username);
        }
        userRepository.setStatusUser(username, status);
    }

    @Override
    public String getAvailableSum(int userId) {
        double availableSum = getSum(userId);
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(availableSum);
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

    @Override
    public List<User> searchByUsername(String username) {
        return userRepository.searchByUsername(username);
    }

    @Override
    public List<User> searchByPhoneNumber(String phoneNum) {
        return userRepository.searchByPhoneNumber(phoneNum);
    }

    @Override
    public List<User> searchByEmail(String email) {
        return userRepository.searchByEmail(email);
    }

    private double getSum(int userId) {
        List<Wallet> list = walletRepository.getWalletsbyUserId(userId);
        return list.stream()
                .mapToDouble(Wallet::getBalance)
                .sum();
    }
}