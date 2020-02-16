package com.example.demo.service;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.helpers.PictureFormat;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserServiceImpl;
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
}
