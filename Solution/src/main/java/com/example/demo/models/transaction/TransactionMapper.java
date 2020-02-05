package com.example.demo.models.transaction;

import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

    public Deposit createDeposit(TransactionDTO transactionDTO) {
        Deposit deposit = new Deposit();
        deposit.setAmount(transactionDTO.getAmount());
        deposit.setCurrency(transactionDTO.getCurrency());
        deposit.setDescription(transactionDTO.getDescription());
        deposit.setIdempotencyKey(UUID.randomUUID().toString());
        deposit.setCardSender(cardDetailsService.getById(transactionDTO.getSenderId()));
        deposit.setReceiver(walletService.getById(transactionDTO.getReceiverId()));
        return deposit;
    }

    public Internal createInternalTransaction(TransactionDTO transactionDTO) {
        Internal internal = new Internal();
        internal.setAmount(transactionDTO.getAmount());
        internal.setDescription(transactionDTO.getDescription());
        internal.setIdempotencyKey(UUID.randomUUID().toString());
        internal.setCurrency(transactionDTO.getCurrency());
        internal.setSender(walletService.getById(transactionDTO.getSenderId()));
        internal.setReceiver(walletService.getById(transactionDTO.getReceiverId()));
        return internal;
    }

    public Withdrawal createWithdrawal(TransactionDTO transactionDTO) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(transactionDTO.getAmount());
        withdrawal.setDescription(transactionDTO.getDescription());
        withdrawal.setIdempotencyKey(UUID.randomUUID().toString());
        withdrawal.setCurrency(transactionDTO.getCurrency());
        withdrawal.setSender(walletService.getById(transactionDTO.getSenderId()));
        withdrawal.setReceiver(cardDetailsService.getById(transactionDTO.getReceiverId()));
        return withdrawal;
    }
}
