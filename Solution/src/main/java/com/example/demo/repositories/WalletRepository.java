package com.example.demo.repositories;

import com.example.demo.models.wallet.Wallet;

public interface WalletRepository {

    Wallet createWallet(Wallet wallet);

    Wallet getById(int id);
}
