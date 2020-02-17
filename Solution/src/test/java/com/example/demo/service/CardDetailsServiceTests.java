package com.example.demo.service;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.exceptions.InvalidPermission;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardMapper;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.repositories.contracts.CardDetailsRepository;
import com.example.demo.services.implementations.CardDetailsServiceImpl;
import com.example.demo.services.contracts.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.service.Factory.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CardDetailsServiceTests {
    @Mock
    CardDetailsRepository cardDetailsRepository;
    @Mock
    UserService userService;
    @Mock
    CardMapper cardMapper;

    @InjectMocks
    CardDetailsServiceImpl cardDetailsService;

    @Test
    public void getByIdShould_CallRepository() {
        //Arrange
        CardDetails cardDetails = createCard();

        Mockito.when(cardDetailsRepository.getById(cardDetails.getId()))
                .thenReturn(cardDetails);

        //Act
        cardDetailsRepository.getById(cardDetails.getId());

        //Assert
        Assert.assertSame(cardDetailsRepository.getById(cardDetails.getId()), cardDetails);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdShould_ThrowExceptionWhenNoCards() {
        Mockito.when(cardDetailsService.checkIfCardIdExists(anyInt()))
                .thenReturn(false);

        //Act
        cardDetailsService.getById(anyInt());
    }

    @Test
    public void getByIdShould_ReturnCardWhenCardExists() {
        CardDetails cardDetails = createCard();
        cardDetails.setId(1);

        Mockito.when(cardDetailsService.checkIfCardIdExists(anyInt()))
                .thenReturn(true);
        Mockito.when(cardDetailsRepository.getById(1))
                .thenReturn(cardDetails);

        //Act
        CardDetails cardDetails1 =  cardDetailsService.getById(1);

        //Assert
        Assert.assertSame(cardDetails1, cardDetails);

    }

    @Test
    public void getByNumberShould_CallRepository() {
        //Arrange
        CardDetails cardDetails = createCard();

        Mockito.when(cardDetailsRepository.getByNumber(cardDetails.getCardNumber()))
                .thenReturn(cardDetails);

        //Act
        cardDetailsRepository.getByNumber(cardDetails.getCardNumber());

        //Assert
        Assert.assertSame(cardDetailsRepository.getByNumber(cardDetails.getCardNumber()), cardDetails);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByNumberShould_ThrowExceptionWhenNoCards() {
        String cardNumber = "cardNumber";
        Mockito.when(cardDetailsService.checkIfCardNumberExists(cardNumber))
                .thenReturn(false);

        //Act
        cardDetailsService.getByNumber(cardNumber);
    }

    @Test
    public void getByNumberShould_ReturnCardWhenCardExists() {
        CardDetails cardDetails = createCard();
        String cardNumber = "cardNumber";
        cardDetails.setCardNumber(cardNumber);

        Mockito.when(cardDetailsService.checkIfCardNumberExists(cardNumber))
                .thenReturn(true);
        Mockito.when(cardDetailsRepository.getByNumber(cardNumber))
                .thenReturn(cardDetails);

        //Act
        CardDetails cardDetails1 =  cardDetailsService.getByNumber(cardNumber);

        //Assert
        Assert.assertSame(cardDetails1, cardDetails);

    }

    @Test(expected = InvalidPermission.class)
    public void createCardShouldThrowException_WhenUserUnconfirmed() {
        //Arrange
        CardRegistrationDTO cardRegistrationDTO = createCardRegistrationDTO();
        User user = createUser();
        user.setUsername("Test");
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        user.setConfirm_identity(false);


        //Act
        cardDetailsService.createCard(cardRegistrationDTO, user.getUsername());
    }

    @Test(expected = InvalidCardException.class)
    public void createCardShouldThrowException_WhenNamesDoNotMatch() {
        //Arrange
        CardRegistrationDTO cardRegistrationDTO = createCardRegistrationDTO();
        User user = createUser();
        user.setUsername("Test");
        cardRegistrationDTO.setCardholderName("Test1");
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        user.setConfirm_identity(true);

        //Act
        cardDetailsService.createCard(cardRegistrationDTO, user.getUsername());
    }

    @Test(expected = DuplicateEntityException.class)
    public void createCardShouldThrowException_WhenCardExists() {
        //Arrange
        CardRegistrationDTO cardRegistrationDTO = createCardRegistrationDTO();
        User user = createUser();
        user.setFirstName("Test");
        user.setLastName("Test");
        cardRegistrationDTO.setCardholderName("Test Test");
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        user.setConfirm_identity(true);
        Mockito.when(cardDetailsService.isCardExist(cardRegistrationDTO.getCardNumber()))
                .thenReturn(true);

        //Act
        cardDetailsService.createCard(cardRegistrationDTO, user.getUsername());
    }

    @Test(expected = InvalidCardException.class)
    public void createCardShouldThrowException_WhenCardExpired() {
        //Arrange
        CardRegistrationDTO cardRegistrationDTO = createCardRegistrationDTO();
        User user = createUser();
        user.setFirstName("Test");
        user.setLastName("Test");
        cardRegistrationDTO.setCardholderName("Test Test");
        cardRegistrationDTO.setExpirationDate("11/18");
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        user.setConfirm_identity(true);
        Mockito.when(cardDetailsService.isCardExist(cardRegistrationDTO.getCardNumber()))
                .thenReturn(false);

        //Act
        cardDetailsService.createCard(cardRegistrationDTO, user.getUsername());
    }

    @Test
    public void createCardShouldCallRepository_Save() {
        //Arrange
        CardRegistrationDTO cardRegistrationDTO = createCardRegistrationDTO();
        User user = createUser();
        user.setFirstName("Test");
        user.setLastName("Test");
        cardRegistrationDTO.setCardholderName("Test Test");
        cardRegistrationDTO.setExpirationDate("11/25");
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        user.setConfirm_identity(true);
        Mockito.when(cardDetailsService.isCardExist(cardRegistrationDTO.getCardNumber()))
                .thenReturn(false);

        //Act
        CardDetails cardDetails = cardDetailsService.createCard(cardRegistrationDTO, user.getUsername());

        //Assert
        Mockito.verify(cardDetailsRepository, Mockito.times(1))
                .createCard(cardDetails);
    }

    @Test(expected = InvalidPermission.class)
    public void updateCardShouldThrowException_WhenUsersDiffer() {
        //Arrange
        CardUpdateDTO cardUpdateDTO = createCardUpdateDTO();
        User currentUser = createUser();
        User cardUser = createUser();
        CardDetails cardDetails = createCard();
        cardDetails.setId(2);
        cardDetails.setUser(currentUser);
        currentUser.setId(1);
        currentUser.setUsername("Test");
        cardDetails.setUser(cardUser);
        Mockito.when(cardDetailsService.checkIfCardIdExists(2))
                .thenReturn(true);
        Mockito.when(userService.getByUsername(currentUser.getUsername()))
                .thenReturn(currentUser);
        Mockito.when(cardDetailsService.getById(cardDetails.getId()))
                .thenReturn(cardDetails);

        //Act
        cardDetailsService.updateCard(cardUpdateDTO, cardDetails.getId(), currentUser.getUsername());
    }

    @Test
    public void updateCardShouldCallRepository_Update() {
        //Arrange
        CardUpdateDTO cardUpdateDTO = createCardUpdateDTO();
        User currentUser = createUser();
        CardDetails cardDetails = createCard();
        cardDetails.setId(2);
        cardDetails.setUser(currentUser);
        currentUser.setId(1);
        currentUser.setUsername("Test");
        cardDetails.setUser(currentUser);
        Mockito.when(cardDetailsService.checkIfCardIdExists(2))
                .thenReturn(true);
        Mockito.when(userService.getByUsername(currentUser.getUsername()))
                .thenReturn(currentUser);
        Mockito.when(cardDetailsService.getById(cardDetails.getId()))
                .thenReturn(cardDetails);

        //Act
        cardDetailsService.updateCard(cardUpdateDTO, cardDetails.getId(), currentUser.getUsername());

        //Assert
        Mockito.verify(cardDetailsRepository, Mockito.times(1))
                .updateCard(cardDetails);
    }

    @Test(expected = EntityNotFoundException.class)
    public void setCardStatusShould_ThrowExceptionWhenCardNotExisting() {
        String cardNumber = "cardNumber";
        Mockito.when(cardDetailsService.checkIfCardNumberExists(cardNumber))
                .thenReturn(false);

        //Act
        cardDetailsService.setCardStatus(cardNumber, true);
    }

    @Test
    public void setCardStatusShouldCallRepository_Update() {
        //Arrange
        String cardNumber = "cardNumber";
        Mockito.when(cardDetailsService.checkIfCardNumberExists(cardNumber))
                .thenReturn(true);

        //Act
        cardDetailsService.setCardStatus(cardNumber, true);

        //Assert
        Mockito.verify(cardDetailsRepository, Mockito.times(1))
                .setCardStatus(cardNumber, true);
    }

    @Test
    public void isUserIsOwnerShouldCallRepository() {

        cardDetailsService.isUserIsOwner(anyInt(), anyInt());

        //Assert
        Mockito.verify(cardDetailsRepository, Mockito.times(1))
                .isUserIsOwner(anyInt(), anyInt());
    }
}
