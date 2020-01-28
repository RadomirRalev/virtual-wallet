package com.example.demo.services;

import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        return walletRepository.createWallet(wallet);
    }

    @Override
    public Wallet getById(int id) {
        return walletRepository.getById(id);
    }
}
