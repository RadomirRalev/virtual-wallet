package com.example.demo.repositories;

import com.example.demo.models.confirmIdentity.ConfirmIdentity;

public interface ConfirmIdentityRepository {

    ConfirmIdentity createConfrimIdentity(ConfirmIdentity confirmIdentity);

    ConfirmIdentity getByUserIdRequestForConfirm(int userId);

    public void setStatus(int userId, boolean status);

    boolean isUserHaveConfirmIdentityRequest(int userId);
}
