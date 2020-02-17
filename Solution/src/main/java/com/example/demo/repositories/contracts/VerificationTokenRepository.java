package com.example.demo.repositories.contracts;

import com.example.demo.models.verificationToken.VerificationToken;

public interface VerificationTokenRepository {

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken getByToken(String token);


}
