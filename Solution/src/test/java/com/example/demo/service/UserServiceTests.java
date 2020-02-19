package com.example.demo.service;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.helpers.PictureFormat;
import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.contracts.RoleRepository;
import com.example.demo.repositories.contracts.UserRepository;
import com.example.demo.repositories.contracts.WalletRepository;
import com.example.demo.services.implementations.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.service.Factory.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Mock
    UserRepository userRepository;
    @Mock
    PictureFormat pictureFormat;
    @Mock
    RoleRepository roleRepository;
    @Mock
    WalletRepository walletRepository;


    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void getUsersShould_ReturnEmptyListWhenNoUsers() {
        Assert.assertTrue(userService.getUsers(PAGE).isEmpty());
    }

    @Test
    public void getUsersShould_ReturnListWhenThereAreUsers() {
        //Arrange
        User user1 = createUser();
        User user2 = createUser();

        Mockito.when(userRepository.getUsers(PAGE))
                .thenReturn(Arrays.asList(user1, user2));
        //Act
        List<User> usersList = userService.getUsers(PAGE);

        //Assert
        Assert.assertEquals(2, usersList.size());
    }

    @Test
    public void getUsersForConfirmShould_ReturnEmptyListWhenNoUsers() {
        Assert.assertTrue(userService.getUsersForConfirm().isEmpty());
    }

    @Test
    public void getUsersForConfirmShould_ReturnListWhenThereAreUsers() {
        //Arrange
        User user1 = createUser();
        User user2 = createUser();

        Mockito.when(userRepository.getUsersForConfirm())
                .thenReturn(Arrays.asList(user1, user2));
        //Act
        List<User> usersList = userService.getUsersForConfirm();

        //Assert
        Assert.assertEquals(2, usersList.size());
    }

    @Test(expected = InvalidPasswordException.class)
    public void createUsersShould_ThrowExceptionWhenPasswordsDonotMatch() throws IOException {
        //Arrange
        UserRegistrationDTO userRegistrationDTO = createUserRegistrationDTO();
        userRegistrationDTO.setPassword("1");
        userRegistrationDTO.setPasswordConfirmation("2");

        //Act
        userService.createUser(userRegistrationDTO);
    }

    @Test(expected = DuplicateEntityException.class)
    public void createUsersShould_ThrowExceptionWhenUserExists() throws IOException {
        //Arrange
        UserRegistrationDTO userRegistrationDTO = createUserRegistrationDTO();
        userRegistrationDTO.setPassword("1");
        userRegistrationDTO.setPasswordConfirmation("1");
        Mockito.when(userService.isUsernameExist(userRegistrationDTO.getUsername()))
                .thenReturn(true);

        //Act
        userService.createUser(userRegistrationDTO);
    }

    @Test(expected = DuplicateEntityException.class)
    public void createUsersShould_ThrowExceptionWhenEmailExists() throws IOException {
        //Arrange
        UserRegistrationDTO userRegistrationDTO = createUserRegistrationDTO();
        userRegistrationDTO.setPassword("1");
        userRegistrationDTO.setPasswordConfirmation("1");
        Mockito.when(userService.isUsernameExist(userRegistrationDTO.getUsername()))
                .thenReturn(false);
        Mockito.when(userService.isEmailExist(userRegistrationDTO.getEmail()))
                .thenReturn(true);

        //Act
        userService.createUser(userRegistrationDTO);
    }

    @Test(expected = DuplicateEntityException.class)
    public void createUsersShould_ThrowExceptionWhenPhoneExists() throws IOException {
        //Arrange
        UserRegistrationDTO userRegistrationDTO = createUserRegistrationDTO();
        userRegistrationDTO.setPassword("1");
        userRegistrationDTO.setPasswordConfirmation("1");
        Mockito.when(userService.isUsernameExist(userRegistrationDTO.getUsername()))
                .thenReturn(false);
        Mockito.when(userService.isEmailExist(userRegistrationDTO.getEmail()))
                .thenReturn(false);
        Mockito.when(userService.isPhoneNumberExist(userRegistrationDTO.getPhoneNumber()))
                .thenReturn(true);
        //Act
        userService.createUser(userRegistrationDTO);
    }

    @Test(expected = InvalidPictureFormat.class)
    public void createUsersShould_ThrowExceptionWhenPictureEmpty() throws IOException {
        //Arrange
        UserRegistrationDTO userRegistrationDTO = createUserRegistrationDTO();
        userRegistrationDTO.setPassword("1");
        userRegistrationDTO.setPasswordConfirmation("1");
        MockMultipartFile file = new MockMultipartFile("user-file", "test.txt",
                null, "test data".getBytes());
        userRegistrationDTO.setFile(file);
        Mockito.when(userService.isUsernameExist(userRegistrationDTO.getUsername()))
                .thenReturn(false);
        Mockito.when(userService.isEmailExist(userRegistrationDTO.getEmail()))
                .thenReturn(false);
        Mockito.when(userService.isPhoneNumberExist(userRegistrationDTO.getPhoneNumber()))
                .thenReturn(false);
        //Act
        userService.createUser(userRegistrationDTO);
    }

    @Test
    public void getUsersByIdShould_ReturnUserWhenUserExists() {
        User user = createUser();
        user.setId(1);


        Mockito.when(userRepository.getById(user.getId()))
                .thenReturn(user);

        //Act
        User userToReturn = userService.getById(user.getId());

        //Assert
        Assert.assertSame(userToReturn, user);

    }

    @Test
    public void getUsersByUsernameShould_ReturnUserWhenUserExists() {
        User user = createUser();
        String username = "Test";
        user.setUsername(username);


        Mockito.when(userRepository.getByUsername(username))
                .thenReturn(user);

        //Act
        User userToReturn = userService.getByUsername(username);

        //Assert
        Assert.assertSame(userToReturn, user);

    }

    @Test
    public void getUsersByPhoneShould_ReturnUserWhenUserExists() {
        User user = createUser();
        String phone = "Test";
        user.setPhoneNumber(phone);


        Mockito.when(userRepository.getByPhoneNumber(phone))
                .thenReturn(user);

        //Act
        User userToReturn = userService.getByPhoneNumber(phone);

        //Assert
        Assert.assertSame(userToReturn, user);

    }

    @Test
    public void getUsersByEmailShould_ReturnUserWhenUserExists() {
        User user = createUser();
        String email = "Test";
        user.setEmail(email);


        Mockito.when(userRepository.getByEmail(email))
                .thenReturn(user);

        //Act
        User userToReturn = userService.getByEmail(email);

        //Assert
        Assert.assertSame(userToReturn, user);

    }

    @Test(expected = InvalidPasswordException.class)
    public void changePasswordShouldThrowExceptionWhen_PasswordsDoNotMatch() {
        User user = createUser();
        PasswordUpdateDTO passwordUpdateDTO = createPasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("1");
        passwordUpdateDTO.setNewPassword("2");

        //Act
        userService.changePassword(user, passwordUpdateDTO);
    }

    @Test(expected = InvalidPasswordException.class)
    public void changePasswordShouldThrowExceptionWhen_EncodedPasswordsDoNotMatch() {
        User user = createUser();
        PasswordUpdateDTO passwordUpdateDTO = createPasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("1");
        passwordUpdateDTO.setNewPassword("1");

        //Act
        userService.changePassword(user, passwordUpdateDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void setStatusUserShouldThrowExceptionWhen_UsernameDoesNotExist() {
        User user = createUser();

        Mockito.when(userService.isUsernameExist(user.getUsername()))
                .thenReturn(false);

        //Act
        userService.setStatusUser(user.getUsername(), true);

    }

    @Test
    public void setStatusUserShouldCallRepository_Save() {
        User user = createUser();

        Mockito.when(userService.isUsernameExist(user.getUsername()))
                .thenReturn(true);

        //Act
        userService.setStatusUser(user.getUsername(), true);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .setStatusUser(user.getUsername(), true);

    }

    @Test(expected = EntityNotFoundException.class)
    public void setBlockedStatusShouldThrowExceptionWhen_UsernameDoesNotExist() {
        User user = createUser();

        Mockito.when(userService.isUsernameExist(user.getUsername()))
                .thenReturn(false);

        //Act
        userService.setBlockedStatus(user.getUsername(), true);

    }

    @Test
    public void setBlockedStatusShouldCallRepository_Save() {
        User user = createUser();

        Mockito.when(userService.isUsernameExist(user.getUsername()))
                .thenReturn(true);

        //Act
        userService.setBlockedStatus(user.getUsername(), true);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .setBlockedStatus(user.getUsername(), true);

    }

    @Test(expected = DuplicateEntityException.class)
    public void setStatusIdentityShouldThrowExceptionWhen_UsernameDoesNotExist() {
        User user = createUser();

        Mockito.when(userService.isIdentityConfirm(user.getUsername()))
                .thenReturn(true);

        //Act
        userService.setStatusIdentity(user.getUsername(), true);

    }

    @Test
    public void setStatusIdentityShouldCallRepository_Save() {
        User user = createUser();

        Mockito.when(userService.isUsernameExist(user.getUsername()))
                .thenReturn(false);

        //Act
        userService.setStatusIdentity(user.getUsername(), true);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .setStatusIdentity(user.getUsername(), true);
    }

    @Test
    public void getAvailableSum_ShouldReturnSum() {
        User user = createUser();
        Wallet wallet = createWallet();
        wallet.setUser(user);
        wallet.setBalance(BALANCE_ENOUGH_DOUBLE);
        List<Wallet> list = new ArrayList<>();
        list.add(wallet);
        String sum = String.valueOf(wallet.getBalance());
        Mockito.when(walletRepository.getWalletsbyUserId(user.getId()))
                .thenReturn(list);

        Assert.assertEquals(sum, userService.getAvailableSum(user.getId()));

    }

    @Test
    public void isBlockedShould_CallRepository() {
        User user = createUser();

        //Act
        userService.isBlocked(user.getUsername());

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .isBlocked(user.getUsername());
    }

    @Test
    public void isEnabledShould_CallRepository() {
        User user = createUser();

        //Act
        userService.isEnabled(user.getUsername());

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .isEnabled(user.getUsername());
    }

    @Test
    public void searchByNameShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByUsername(user.getUsername(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByUsername(user.getUsername(), PAGE);
    }

    @Test
    public void searchByPhoneNumberShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByPhoneNumber(user.getPhoneNumber(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByPhoneNumber(user.getPhoneNumber(), PAGE);
    }

    @Test
    public void searchByEmailNumberShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByEmail(user.getEmail(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByEmail(user.getEmail(), PAGE);
    }

    @Test
    public void searchByUsernameAsAdminShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByUsernameAsAdmin(user.getUsername(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByUsernameAsAdmin(user.getUsername(), PAGE);
    }

    @Test
    public void searchByPhoneNumberAsAdminShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByPhoneNumberAsAdmin(user.getPhoneNumber(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByPhoneNumberAsAdmin(user.getPhoneNumber(), PAGE);
    }

    @Test
    public void searchByEmailAsAdminAsAdminShould_CallRepository() {
        User user = createUser();

        //Act
        userService.searchByEmailAsAdmin(user.getEmail(), PAGE);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .searchByEmailAsAdmin(user.getEmail(), PAGE);
    }

    @Test
    public void checkIfUserExistsShould_CallRepository() {
        User user = createUser();

        //Act
        userService.checkIfUserIdExists(user.getId());

        //Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .checkIfUserIdExists(user.getId());
    }

}
