package com.example.demo.models.card;

import com.example.demo.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private int id;
    @Column(name = "number")
    private String cardNumber;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "security_code")
    private String securityCode;
    @Column(name = "cardholder_name")
    private String cardholderName;
    @Column(name = "enabled")
    private boolean enabled;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public CardDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        this.cardNumber = number;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
