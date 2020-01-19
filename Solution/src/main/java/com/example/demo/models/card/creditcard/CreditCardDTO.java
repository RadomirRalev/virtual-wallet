package com.example.demo.models.card.creditcard;

import javax.validation.constraints.Pattern;

public class CreditCardDTO {

    private int id;
    //TODO
//@Pattern(regexp = "\\b4[0-9]{3}[-][0-9]{4}[-][0-9]{4}[-][0-9](?:[0-9]{3})?", message = "Visa regex")
    private String number;
    @Pattern(regexp = "^\\d{2}\\/\\d{2}$", message = "Invalid expiration date")
    private String expirationDate;
    @Pattern(regexp = "^[0-9]{3,4}+$", message = "Invalid security code")
    private int securityCode;
    private int status;

    public CreditCardDTO(int id, String number, String expirationDate, int securityCode, int status) {
        this.id = id;
        this.number = number;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
