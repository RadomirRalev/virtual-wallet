package com.example.demo.repositories;

import com.example.demo.models.user.ConfirmIdentity;

public interface ConfirmIdentityRepository {

    ConfirmIdentity createConfrimIdentity(ConfirmIdentity confirmIdentity);

    ConfirmIdentity getByUserId(int userId);
}
