package com.example.demo.services;

import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;

public interface WalletService {

    Wallet createWallet(WalletCreationDTO walletCreationDTO, int userId);

    Wallet getById(int id);

    boolean checkIfWalletIdExists(int id);
}
