package com.example.demo.services;

import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.transaction.TransactionMapper;
import com.example.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.EXPIRATION_DATE_IS_INVALID;
import static com.example.demo.constants.ExceptionConstants.SENDER_FUNDS_ARE_NOT_SUFFICIENT;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.getTransactions();
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int id) {
        return transactionRepository.getTransactionsbyWalletId(id);
    }

    @Override
    public Internal createInternal(TransactionDTO transactionDTO) {
        Internal internal = transactionMapper.createInternalTransaction(transactionDTO);
        if (internal.getSender().getBalance() - internal.getAmount() < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
        int balanceSender = internal.getSender().getBalance() - internal.getAmount();
        int balanceReceiver = internal.getReceiver().getBalance() + internal.getAmount();
        return transactionRepository.createInternal(internal, balanceSender, balanceReceiver);
    }
}
