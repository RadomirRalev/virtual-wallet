package com.example.demo.models.transaction;

import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TransactionMapper {

    private UserService userService;
    private CardDetailsService cardDetailsService;
    private WalletService walletService;


    @Autowired
    public TransactionMapper(UserService userService, CardDetailsService cardDetailsService, WalletService walletService) {
        this.userService = userService;
        this.cardDetailsService = cardDetailsService;
        this.walletService = walletService;
    }

    public Transaction createDeposit(TransactionDTO transactionDTO) {
        Deposit deposit = new Deposit();
        deposit.setAmount(transactionDTO.getAmount());
        deposit.setDescription(transactionDTO.getDescription());
        deposit.setIdempotencyKey(transactionDTO.getIdempotencyKey());
        deposit.setCurrency(transactionDTO.getCurrency());
        deposit.setCardSender(cardDetailsService.getById(transactionDTO.getSenderId()));
        return deposit;
    }

    public Internal createInternalTransaction(TransactionDTO transactionDTO) {
        Internal internal = new Internal();
        internal.setAmount(transactionDTO.getAmount());
        internal.setDescription(transactionDTO.getDescription());
        internal.setDescription(transactionDTO.getIdempotencyKey());
        internal.setCurrency(transactionDTO.getCurrency());
        internal.setSender(walletService.getById(transactionDTO.getSenderId()));
        internal.setReceiver(walletService.getById(transactionDTO.getReceiverId()));
        return internal;
    }

    public Withdrawal createWithdrawal(TransactionDTO transactionDTO) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(transactionDTO.getAmount());
        withdrawal.setDescription(transactionDTO.getDescription());
        withdrawal.setDescription(transactionDTO.getIdempotencyKey());
        withdrawal.setCurrency(transactionDTO.getCurrency());
        withdrawal.setSender(walletService.getById(transactionDTO.getSenderId()));
        withdrawal.setReceiver(cardDetailsService.getById(transactionDTO.getReceiverId()));
        return withdrawal;
    }
}
