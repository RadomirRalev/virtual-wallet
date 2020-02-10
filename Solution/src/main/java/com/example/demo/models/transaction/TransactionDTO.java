package com.example.demo.models.transaction;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class TransactionDTO {

    private double amount;
    private int senderId;
    private int receiverId;
    private String type;
    @Length(min = 2, max = 200, message = "Description should be between 2 and 200 symbols.")
    private String description;
    private String idempotencyKey;
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be valid ISO code.")
    private String currency;
    private String senderName;
    private String receiverName;

    public TransactionDTO() {
    }

    public TransactionDTO(double amount, int senderId, String type, String description, String idempotencyKey, int receiverId, String currency) {
        this.amount = amount;
        this.senderId = senderId;
        this.type = type;
        this.description = description;
        this.idempotencyKey = idempotencyKey;
        this.receiverId = receiverId;
        this.currency = currency;
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

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
