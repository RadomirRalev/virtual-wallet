package com.example.demo.models.card;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class CardUpdateDTO {

    @Pattern(regexp = "[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}$", message = "Invalid card number.")
    private String cardNumber;
    @Pattern(regexp = "^\\d{2}\\/\\d{2}$", message = "Invalid expiration date.")
    private String expirationDate;
    @Pattern(regexp = "^[0-9]{3}$", message = "Invalid security code.")
    private String securityCode;
    @Length(min = 2, max = 40)

    public CardUpdateDTO() {
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

}
