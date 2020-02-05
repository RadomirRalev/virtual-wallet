package com.example.demo.models.verificationToken;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class VerificationTokenMapper {

    private static final String EMAIL = "virtualwalletrrvv@gmail.com";
    private static final String CONFIRM_MESSAGE = "To confirm your account, please click here : ";
    private static final String CONFIRM_URL_ADDRESS = "http://localhost:8181/confirm-account?token=";

    public VerificationToken createVerificationToken(int userId) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser_id(userId);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setCreatedDate(createdDate());
        return verificationToken;
    }

    public SimpleMailMessage createEmail(String userEmail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom(EMAIL);
        mailMessage.setText(CONFIRM_MESSAGE + CONFIRM_URL_ADDRESS + token);
        return mailMessage;
    }

    private Date createdDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        return new Date(cal.getTime().getTime());
    }

}
