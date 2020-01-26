package com.example.demo.services;

import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);
}
