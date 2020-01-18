package com.example.demo.models.user;

import com.example.demo.models.card.CreditCard;
import com.example.demo.models.card.DebitCard;


import javax.persistence.*;
import java.util.Base64;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username")
    private String username;
    @Column(name = "enabled")
    private int enabled;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "debit_card")
    private DebitCard debitCard;
    @OneToOne
    @JoinColumn(name = "credit_card")
    private CreditCard creditCard;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_username"),
    inverseJoinColumns = @JoinColumn(name = "role_username"))
    private Set<Role> roles;


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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
