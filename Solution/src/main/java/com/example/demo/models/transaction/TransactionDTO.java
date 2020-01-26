package com.example.demo.models.transaction;

public class TransactionDTO {

    private int amount;
    private int senderId;
    private int receiverId;
    private String type;
    private String description;
    private String idempotencyKey;
    private String currency;

    public TransactionDTO(int amount, int senderId, String type, String description, String idempotencyKey, int receiverId, String currency) {
        this.amount = amount;
        this.senderId = senderId;
        this.type = type;
        this.description = description;
        this.idempotencyKey = idempotencyKey;
        this.receiverId = receiverId;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
