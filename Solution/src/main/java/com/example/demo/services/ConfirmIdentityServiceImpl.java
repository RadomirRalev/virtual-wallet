package com.example.demo.services;

import com.example.demo.models.user.ConfirmIdentity;
import com.example.demo.models.user.ConfirmIdentityMapper;
import com.example.demo.models.user.ConfirmIdentityRegistrationDTO;
import com.example.demo.repositories.ConfirmIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConfirmIdentityServiceImpl implements ConfirmIdentityService {

    private ConfirmIdentityRepository confirmIdentityRepository;

    @Autowired
    public ConfirmIdentityServiceImpl(ConfirmIdentityRepository confirmIdentityRepository) {
        this.confirmIdentityRepository = confirmIdentityRepository;
    }

    @Override
    public ConfirmIdentity createConfrimIdentity(ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO,
                                                 int userId)
            throws IOException {
        ConfirmIdentity confirmIdentity = ConfirmIdentityMapper.mapConfirmIdentity(confirmIdentityRegistrationDTO);
        confirmIdentity.setUserId(userId);
        return confirmIdentityRepository.createConfrimIdentity(confirmIdentity);
    }

    @Override
    public ConfirmIdentity getByUserId(int userId) {
        return confirmIdentityRepository.getByUserId(userId);
    }
}
