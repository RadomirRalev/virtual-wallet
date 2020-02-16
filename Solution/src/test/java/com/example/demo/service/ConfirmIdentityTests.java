package com.example.demo.service;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.User;
import com.example.demo.repositories.ConfirmIdentityRepository;
import com.example.demo.services.ConfirmIdentityServiceImpl;
import com.example.demo.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.service.Factory.createConfirmIdentityRegistrationDTO;
import static com.example.demo.service.Factory.createUser;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ConfirmIdentityTests {
    @Mock
    ConfirmIdentityRepository confirmIdentityRepository;
    @Mock
    UserService userService;

    @InjectMocks
    ConfirmIdentityServiceImpl confirmIdentityService;

    @Test(expected = DuplicateEntityException.class)
    public void createConfrimIdentity_ThrowExceptionWhenIdentityAlreadyConfirmed() throws IOException {
        User user = createUser();
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        Mockito.when(confirmIdentityRepository.isUserHaveConfirmIdentityRequest(user.getId()))
                .thenReturn(true);

        //Act
        confirmIdentityService.createConfrimIdentity(createConfirmIdentityRegistrationDTO(), user.getUsername());
    }

    @Test(expected = InvalidPictureFormat.class)
    public void createConfrimIdentity_ThrowExceptionWhenPictureFormatWrong() throws IOException {
        User user = createUser();
        ConfirmIdentityRegistrationDTO confirmDTO = createConfirmIdentityRegistrationDTO();
        byte[] content = new byte[10];
        MultipartFile file = new MockMultipartFile("name", content);
        confirmDTO.setFront_picture(file);
        Mockito.when(userService.getByUsername(user.getUsername()))
                .thenReturn(user);
        Mockito.when(confirmIdentityRepository.isUserHaveConfirmIdentityRequest(user.getId()))
                .thenReturn(false);

        //Act
        confirmIdentityService.createConfrimIdentity(confirmDTO, user.getUsername());
    }
}
