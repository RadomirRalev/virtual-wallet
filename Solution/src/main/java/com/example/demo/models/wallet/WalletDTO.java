package com.example.demo.models.wallet;

import com.example.demo.models.user.User;

public class WalletDTO {

    private double amount;
    private int owner;

    public WalletDTO(double amount, int owner) {
        this.amount = amount;
        this.owner = owner;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
