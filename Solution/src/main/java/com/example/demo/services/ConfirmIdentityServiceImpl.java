package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.helpers.PictureFormat;
import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import com.example.demo.models.confirmIdentity.ConfirmIdentityMapper;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.User;
import com.example.demo.repositories.ConfirmIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.demo.constants.ExceptionConstants.*;

@Service
public class ConfirmIdentityServiceImpl implements ConfirmIdentityService {

    private ConfirmIdentityRepository confirmIdentityRepository;
    private UserService userService;

    @Autowired
    public ConfirmIdentityServiceImpl(ConfirmIdentityRepository confirmIdentityRepository, UserService userService) {
        this.confirmIdentityRepository = confirmIdentityRepository;
        this.userService = userService;
    }

    @Override
    public ConfirmIdentity createConfrimIdentity(ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO,
                                                 String username) throws IOException {
        User user = userService.getByUsername(username);
        if (isUserHaveConfirmIdentityRequest(user.getId())) {
            throw new DuplicateEntityException(String.format(USER_ALREADY_CONFIRMED_HIS_IDENTITY, username));
        }
        if (!PictureFormat.isPictureJPG(confirmIdentityRegistrationDTO.getFront_picture())) {
            throw new InvalidPictureFormat(ALLOW_PICTURE_FORMAT);
        }
        if (!PictureFormat.isPictureJPG(confirmIdentityRegistrationDTO.getRear_picture())) {
            throw new InvalidPictureFormat(ALLOW_PICTURE_FORMAT);
        }
        if (!PictureFormat.isPictureJPG(confirmIdentityRegistrationDTO.getSelfie())) {
            throw new InvalidPictureFormat(ALLOW_PICTURE_FORMAT);
        }
        ConfirmIdentity confirmIdentity = ConfirmIdentityMapper.mapConfirmIdentity(confirmIdentityRegistrationDTO);
        confirmIdentity.setUserId(user.getId());
        return confirmIdentityRepository.createConfrimIdentity(confirmIdentity);
    }

    @Override
    public ConfirmIdentity getByUserIdRequestForConfirm(int userId) {
        return confirmIdentityRepository.getByUserIdRequestForConfirm(userId);
    }

    @Override
    public void setStatus(int userId, boolean status) {
        confirmIdentityRepository.setStatus(userId, status);
    }

    @Override
    public boolean isUserHaveConfirmIdentityRequest(int userId) {
        return confirmIdentityRepository.isUserHaveConfirmIdentityRequest(userId);
    }
}

