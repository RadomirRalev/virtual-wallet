package com.example.demo.services.contracts;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    public void sendEmail(SimpleMailMessage email);
}
