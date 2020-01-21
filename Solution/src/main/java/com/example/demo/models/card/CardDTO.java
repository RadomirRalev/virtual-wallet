package com.example.demo.models.card;

public class CardDTO {

    private String number;
    private String expirationDate;
    private int securityCode;

    public CardDTO() {
    }

    public CardDTO(String number, String expirationDate, int securityCode) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
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
}
