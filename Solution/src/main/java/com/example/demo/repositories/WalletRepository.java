package com.example.demo.repositories;

import com.example.demo.models.wallet.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet createWallet(Wallet wallet);

    Wallet getById(int id);

    boolean checkIfWalletIdExists(int id);

    List<Wallet> getWalletsbyUserId(int userId);

    Wallet updateWallet(Wallet walletToBeUpdated);
}
