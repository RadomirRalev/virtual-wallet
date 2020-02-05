package com.example.demo.services;

import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;

import java.util.List;

public interface WalletService {

    Wallet createWallet(WalletCreationDTO walletCreationDTO, int userId);

    Wallet getById(int id);

    boolean checkIfWalletIdExists(int id);

    List<Wallet> getWalletsbyUserId(int userId);

    Wallet updateWallet(Wallet walletToBeUpdated);

    Wallet setAsDefault(Wallet walletToBeUpdated);
}
