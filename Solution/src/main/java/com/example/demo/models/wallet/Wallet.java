package com.example.demo.models.wallet;

import com.example.demo.models.user.User;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private int id;
    @Column(name = "amount")
    private double amount;
    @OneToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    public Wallet() {
    }

    public Wallet(int id, double amount, User owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
