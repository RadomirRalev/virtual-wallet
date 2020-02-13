package com.example.demo.services;

import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.User;

import java.io.IOException;

public interface ConfirmIdentityService {

    ConfirmIdentity createConfrimIdentity(ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO, String username)
            throws IOException;

    ConfirmIdentity getByUserIdRequestForConfirm(int userId);

    public void setStatus(int userId, boolean status);

    boolean isUserHaveConfirmIdentityRequest(int userId);

}
