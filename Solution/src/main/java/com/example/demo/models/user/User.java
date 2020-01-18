package com.example.demo.models.user;

import com.example.demo.models.card.CreditCard;
import com.example.demo.models.card.DebitCard;

import java.util.Base64;


public class User {

    private String username;

    private int enabled;

    private String email;

    private String password;

    private DebitCard debitCard;

    private CreditCard creditCard;

    private String phoneNumber;

    private byte[] picture;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(DebitCard debitCard) {
        this.debitCard = debitCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return Base64.getEncoder().encodeToString(picture);
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
