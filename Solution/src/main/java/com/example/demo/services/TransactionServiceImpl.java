package com.example.demo.services;

import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
@Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        return null;
    }
}
