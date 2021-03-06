package com.example.demo.services.contracts;

import com.example.demo.models.verificationToken.VerificationToken;

public interface VerificationTokenService {

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken getByToken(String token);
}
