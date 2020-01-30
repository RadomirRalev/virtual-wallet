package com.example.demo.services;

import com.example.demo.models.wallet.Wallet;

public interface WalletService {

    Wallet createWallet(Wallet wallet);

    Wallet getById(int id);

    boolean checkIfWalletIdExists(int id);
}
