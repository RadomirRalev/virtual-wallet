package com.example.demo.models.card;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class CardRegistrationDTO {
    @Pattern(regexp = "[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}$", message = "Invalid card number.")
    private String cardNumber;
    @Pattern(regexp = "^\\d{2}\\/\\d{2}$", message = "Invalid expiration date.")
    private String expirationDate;
    @Pattern(regexp = "^[0-9]{3}$", message = "Invalid security code.")
    private String securityCode;
    @Length(min = 2, max = 40)
    @Pattern(regexp = "^[A-Z a-z]+[A-Z a-z]+$",
            message = "The accepted characters are upper, lower letters and spaces.")
    private String cardholderName;
    @Length(min = 2, max = 50)
    private String cardName;
    private int user_id;

    public CardRegistrationDTO() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
