package com.example.demo.models.wallet;

import com.example.demo.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private int id;
    @Column(name = "balance")
    private double balance;
    @Column(name = "name")
    private String name;
    @Column(name = "is_default")
    private boolean isWalletDefault;
    @Column(name = "is_deleted")
    private boolean isWalletDeleted;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWalletDefault(boolean isWalletDefault) {
        this.isWalletDefault = isWalletDefault;
    }

    public boolean isWalletDefault() {
        return isWalletDefault;
    }

    public void setIsWalletDeleted(boolean isWalletDeleted) {
        this.isWalletDeleted = isWalletDeleted;
    }

    public boolean getIsWalletDeleted() {
        return isWalletDeleted;
    }

}
