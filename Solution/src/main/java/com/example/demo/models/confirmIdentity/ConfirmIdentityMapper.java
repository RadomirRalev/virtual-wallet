package com.example.demo.models.confirmIdentity;

import java.io.IOException;

public class ConfirmIdentityMapper {

    public static ConfirmIdentity mapConfirmIdentity(ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO)
            throws IOException {
        ConfirmIdentity confirmIdentity = new ConfirmIdentity();
        confirmIdentity.setFront_picture(confirmIdentityRegistrationDTO.getFront_picture().getBytes());
        confirmIdentity.setRear_picture(confirmIdentityRegistrationDTO.getRear_picture().getBytes());
        confirmIdentity.setSelfie(confirmIdentityRegistrationDTO.getSelfie().getBytes());
        confirmIdentity.setHaveRequest(true);
        return confirmIdentity;
    }
}
