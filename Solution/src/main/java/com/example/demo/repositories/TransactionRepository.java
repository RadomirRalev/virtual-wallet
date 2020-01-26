package com.example.demo.repositories;

import com.example.demo.models.transaction.Transaction;

import java.util.List;

public interface TransactionRepository {
    Transaction createTransaction(Transaction transaction);

    List<Transaction> getTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);
}
