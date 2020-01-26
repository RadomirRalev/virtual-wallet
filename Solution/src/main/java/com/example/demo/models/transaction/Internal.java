package com.example.demo.models.transaction;

import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Internal extends Transaction {
    @OneToOne
    @JoinColumn(name = "sender_id")
    private Wallet sender;
    @OneToOne
    @JoinColumn(name = "receiver_id")
    private Wallet receiver;

    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }
}