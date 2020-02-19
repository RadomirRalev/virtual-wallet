package com.example.demo.models.wallet;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class WalletUpdateDTO {
    @NotNull
    @Size(max = 11, message = "Walled id too long")
    private int id;
    @Size(max = 50, message = "Wallet name too long")
    private String name;
    @NotNull
    private boolean isWalletDefault;
    @NotNull
    private boolean isWalletDeleted;

    public WalletUpdateDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWalletDefault() {
        return isWalletDefault;
    }

    public void setWalletDefault(boolean walletDefault) {
        this.isWalletDefault = walletDefault;
    }

    public boolean isWalletDeleted() {
        return isWalletDeleted;
    }

    public void setWalletDeleted(boolean isWalletDeleted) {
        this.isWalletDeleted = isWalletDeleted;
    }

}
