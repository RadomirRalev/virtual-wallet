package com.example.demo.models.transaction;


public class TransactionDTO {

    private double amount;
    private int senderId;
    private int receiverId;
    private int type;

    public TransactionDTO(double amount, int senderId, int receiverId, int type) {
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
