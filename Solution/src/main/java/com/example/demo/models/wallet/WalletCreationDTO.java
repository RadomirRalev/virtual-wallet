package com.example.demo.models.wallet;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class WalletCreationDTO {
    @NotNull
    private int id;
    @NotNull
    private double balance;
    @NotNull
    private int userId;
    @NotNull
    @Size(max = 50, message = "Wallet name too long")
    private String name;

    public WalletCreationDTO() {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
