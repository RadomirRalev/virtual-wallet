package com.example.demo.services;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.wallet.Wallet;

public interface WalletService {

    Wallet createWallet();

    Wallet getById(int id);
}
