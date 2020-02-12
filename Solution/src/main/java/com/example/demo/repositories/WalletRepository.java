package com.example.demo.repositories;

import com.example.demo.models.wallet.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet createWallet(Wallet wallet);

    Wallet getWalletById(int walletId);

    boolean checkIfWalletIdExists(int walletId);

    List<Wallet> getWalletsbyUserId(int userId);

    Wallet updateWallet(Wallet walletToBeUpdated);

    Wallet getDefaultWallet(int userId);

    Wallet disableDefaultWallet(int walletId);

    Wallet setAsDefault(Wallet walletToBeUpdated);

}
