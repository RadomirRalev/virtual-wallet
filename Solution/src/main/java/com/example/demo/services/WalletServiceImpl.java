package com.example.demo.services;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.constants.ExceptionConstants.WALLET_WITH_ID_NOT_EXISTS;

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
        if (checkIfWalletIdExists(id)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
        }
        return walletRepository.getById(id);
    }

    @Override
    public boolean checkIfWalletIdExists(int id) { return walletRepository.checkIfWalletIdExists(id); };
}
