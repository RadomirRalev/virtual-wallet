package com.example.demo.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    public void sendEmail(SimpleMailMessage email);
}
