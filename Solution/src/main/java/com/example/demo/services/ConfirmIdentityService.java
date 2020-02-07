package com.example.demo.services;

import com.example.demo.models.user.ConfirmIdentity;
import com.example.demo.models.user.ConfirmIdentityRegistrationDTO;

import java.io.IOException;

public interface ConfirmIdentityService {

    ConfirmIdentity createConfrimIdentity(ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO, int userId)
            throws IOException;

    ConfirmIdentity getByUserId(int userId);
}
