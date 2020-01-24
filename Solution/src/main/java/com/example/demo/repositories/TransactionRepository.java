package com.example.demo.repositories;

import com.example.demo.models.transaction.Transaction;

public interface TransactionRepository {
    Transaction createTransaction(Transaction transaction);
}
