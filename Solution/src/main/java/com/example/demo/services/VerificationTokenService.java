package com.example.demo.services;

import com.example.demo.models.verificationToken.VerificationToken;

public interface VerificationTokenService {

    public VerificationToken create(VerificationToken verificationToken);

    public VerificationToken getByToken(String token);
}
