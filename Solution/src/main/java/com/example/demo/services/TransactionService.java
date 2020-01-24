package com.example.demo.services;

import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO);
}
