package com.example.demo.services;

import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.transaction.Withdrawal;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    Internal createInternal(TransactionDTO transactionDTO);

    Withdrawal createWithdrawal(TransactionDTO transactionDTO);

}
