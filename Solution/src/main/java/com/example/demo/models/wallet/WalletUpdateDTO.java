package com.example.demo.models.wallet;

public class WalletUpdateDTO {
    private int id;
    private String name;
    private boolean isWalletDefault;

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
        isWalletDefault = walletDefault;
    }

}
