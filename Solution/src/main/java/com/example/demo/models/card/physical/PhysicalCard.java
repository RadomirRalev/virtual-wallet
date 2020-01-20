package com.example.demo.models.card.physical;

import javax.persistence.*;

@Entity
@Table(name = "physical_card")
public class PhysicalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "number")
    private String number;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "security_code")
    private int securityCode;
    @Column(name = "type")
    //TODO logic for check is card debit or credit !
    private String type;
    @Column(name = "status")
    private int status;

    public PhysicalCard() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
