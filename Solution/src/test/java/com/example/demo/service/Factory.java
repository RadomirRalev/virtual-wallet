package com.example.demo.service;

import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;

public class Factory {
    public static final int INDEX = 1;

    public static Internal createInternal(){
        return new Internal();
    }

    public static Withdrawal createWithdrawal(){
        return new Withdrawal();
    }

    public static Deposit createDeposit(){
        return new Deposit();
    }

    public static Wallet createWallet(){
        Wallet wallet = new Wallet();
        wallet.setId(INDEX);
        return wallet;
    }

    public static TransactionDTO createTransactionDTO(){
        return new TransactionDTO(1, 1, "t", "d", "i", 8, "bgn");
    }


}
