package com.example.demo.models.wallet;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    UserService userService;

    @Autowired
    public WalletMapper(UserService userService) {
        this.userService = userService;
    }

    public Wallet creatWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setAmount(walletDTO.getAmount());
        wallet.setOwner(userService.getById(walletDTO.getOwner()));
        return wallet;
    }
}
