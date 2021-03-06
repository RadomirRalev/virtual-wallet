package com.example.demo.services.contracts;

import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;

import java.util.List;

public interface WalletService {

    Wallet createWallet(WalletCreationDTO walletCreationDTO, int userId);

    Wallet getWalletById(int id);

    boolean checkIfWalletIdExists(int id);

    List<Wallet> getWalletsbyUserId(int userId);

    Wallet updateWallet(Wallet walletToBeUpdated);

    Wallet setAsDefault(Wallet walletToBeUpdated, User user);

    Wallet getDefaultWallet(int userId);

    Wallet deleteWallet(Wallet walletToBeUpdated);
}
