package com.example.demo.service;

import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.repositories.VerificationTokenRepository;
import com.example.demo.services.VerificationTokenServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.service.Factory.createVerificationToken;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class VerificationTokenServiceTests {
    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    VerificationTokenServiceImpl verificationTokenService;

    @Test
    public void createShouldCallRepository_Save() {
        //Arrange
        VerificationToken verificationToken = createVerificationToken();

        //Act
        verificationTokenService.create(verificationToken);

        //Assert
        Mockito.verify(verificationTokenRepository, Mockito.times(1))
                .create(verificationToken);
    }

    @Test
    public void getByTokenShould_ReturnToken() {
        //Arrange
        VerificationToken verificationToken = createVerificationToken();
        verificationToken.setToken("Token");
        String token = "Token";
        Mockito.when(verificationTokenRepository.getByToken(token))
                .thenReturn(verificationToken);

        //Act
        VerificationToken returnedVerificationToken = verificationTokenService.getByToken(token);

        //Assert
        Assert.assertSame(returnedVerificationToken, verificationToken);
    }
}
