package com.example.demo.services;

import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken create(VerificationToken verificationToken) {
        return verificationTokenRepository.create(verificationToken);
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenRepository.getByToken(token);
    }


}
