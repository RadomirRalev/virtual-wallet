package com.example.demo.models.wallet;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;

@Component
public class WalletMapper {
    private UserService userService;

    @Autowired
    public WalletMapper(UserService userService) {
        this.userService = userService;
    }

    public Wallet mapWallet(WalletCreationDTO walletCreationDTO) {
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setName(walletCreationDTO.getName());
        wallet.setWalletDefault(DISABLE);
        wallet.setUser(userService.getById(walletCreationDTO.getUserId()));
        return wallet;
    }
}
