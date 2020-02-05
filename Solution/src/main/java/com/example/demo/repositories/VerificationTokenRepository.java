package com.example.demo.repositories;

import com.example.demo.models.verificationToken.VerificationToken;

public interface VerificationTokenRepository {

    public VerificationToken create(VerificationToken verificationToken);

    public VerificationToken getByToken(String token);


}
