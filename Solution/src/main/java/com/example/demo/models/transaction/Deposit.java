package com.example.demo.models.transaction;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.wallet.Wallet;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;

@Entity
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Deposit extends Transaction {
    @OneToOne
    @JoinColumn(name = "sender_card_id")
    private CardDetails sender;
    @OneToOne
    @JoinColumn(name = "wallet_receiver_id")
    private Wallet receiver;
    public Deposit() {
    }

    @Override
    public CardDetails getSender() {
        return sender;
    }

    public void setCardSender(CardDetails sender) {
        this.sender = sender;
    }

    @Override
    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }

}

