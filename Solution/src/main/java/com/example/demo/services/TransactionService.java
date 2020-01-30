package com.example.demo.services;

import com.example.demo.models.transaction.*;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    Internal createInternal(TransactionDTO transactionDTO);

    Withdrawal createWithdrawal(TransactionDTO transactionDTO);

    Deposit createDeposit(TransactionDTO transactionDTO);

    boolean checkIfIdempotencyKeyExists(String idempotencyKey);

}
