package com.example.demo.service;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;

public class Factory {
    public static final int INDEX = 1;
    public static final int TRANSACTION_AMOUNT = 100;
    public static final int TRANSACTION_AMOUNT_HIGHER_THAN_BALANCE = 1000;
    public static final int BALANCE_ENOUGH = 101;
    public static final int BALANCE_NOT_ENOUGH = 1;
    public static UserService userService;
    public static CardDetailsService cardDetailsService;
    public static WalletService walletService;


    public static Internal createInternal(){
        return new Internal();
    }

    public static Withdrawal createWithdrawal(){
        return new Withdrawal();
    }

    public static Deposit createDeposit(){
        return new Deposit();
    }

    public static CardDetails createCard(){
        return new CardDetails();
    }

    public static TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);


    public static Wallet createWallet(){
        Wallet wallet = new Wallet();
        wallet.setId(INDEX);
        wallet.setBalance(BALANCE_ENOUGH);
        return wallet;
    }

    public static TransactionDTO createTransactionDTO(){
        return new TransactionDTO(1, 1, "t", "d", "i", 8, "bgn");
    }


    public static void generateWithdrawalWithSufficientAmount(Withdrawal withdrawal, CardDetails cardDetails, Wallet wallet) {
        cardDetails.setId(INDEX);
        withdrawal.setAmount(TRANSACTION_AMOUNT);
        withdrawal.setReceiver(cardDetails);
        wallet.setBalance(BALANCE_ENOUGH);
        withdrawal.setSender(wallet);
    }

    public static void generateWithdrawalWithInSufficientAmount(Withdrawal withdrawal, CardDetails cardDetails, Wallet wallet) {
        cardDetails.setId(INDEX);
        withdrawal.setAmount(TRANSACTION_AMOUNT);
        withdrawal.setReceiver(cardDetails);
        wallet.setBalance(BALANCE_NOT_ENOUGH);
        withdrawal.setSender(wallet);
    }

    public static void generateInternalWithInSufficientAmount(Internal internal, Wallet sender, Wallet receiver) {
        sender.setId(INDEX);
        sender.setBalance(BALANCE_ENOUGH);
        internal.setAmount(TRANSACTION_AMOUNT_HIGHER_THAN_BALANCE);
        internal.setSender(receiver);
        internal.setReceiver(receiver);
    }

    public static void generateInternalWithSufficientAmount(Internal internal, Wallet sender, Wallet receiver) {
        sender.setId(INDEX);
        sender.setBalance(BALANCE_ENOUGH);
        internal.setAmount(TRANSACTION_AMOUNT);
        internal.setSender(receiver);
        internal.setReceiver(receiver);
    }
}
