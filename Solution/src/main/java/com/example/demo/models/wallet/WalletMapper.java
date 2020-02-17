package com.example.demo.models.wallet;

import com.example.demo.models.user.User;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;
@Component
public class WalletMapper {
    public static final String MY_WALLET = "My wallet";

    private UserService userService;

    @Autowired
    public WalletMapper(UserService userService) {
        this.userService = userService;
    }

    public static Wallet createDefaultWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setName(MY_WALLET);
        wallet.setWalletDefault(ENABLE);
        wallet.setUser(user);
        return wallet;
    }

    public Wallet createWallet(WalletCreationDTO walletCreationDTO) {
        Wallet wallet = new Wallet();
        wallet.setName(walletCreationDTO.getName());
        wallet.setWalletDefault(DISABLE);
        wallet.setUser(userService.getById(walletCreationDTO.getUserId()));
        return wallet;
    }
}
