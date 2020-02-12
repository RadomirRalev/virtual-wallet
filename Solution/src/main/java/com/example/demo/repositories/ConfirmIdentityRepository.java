package com.example.demo.repositories;

import com.example.demo.models.confirmIdentity.ConfirmIdentity;

public interface ConfirmIdentityRepository {

    ConfirmIdentity createConfrimIdentity(ConfirmIdentity confirmIdentity);

    ConfirmIdentity getByUserId(int userId);

    boolean isUserHaveConfirmIdentity (int userId);
}
