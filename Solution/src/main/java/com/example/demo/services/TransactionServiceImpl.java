package com.example.demo.services;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.*;
import com.example.demo.repositories.CardDetailsRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.WalletRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CardDetailsRepository cardDetailsRepository;
    private WalletRepositoryImpl walletRepository;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardDetailsRepository cardDetailsRepository,
                                  TransactionMapper transactionMapper, WalletRepositoryImpl walletRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.cardDetailsRepository = cardDetailsRepository;
        this.walletRepository = walletRepository;
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

        int senderId = internal.getSender().getId();
        checkIfWalletExists(senderId);
        int receiverId = internal.getReceiver().getId();
        checkIfWalletExists(receiverId);
        int balanceSender = internal.getSender().getBalance() - internal.getAmount();
        int balanceReceiver = internal.getReceiver().getBalance() + internal.getAmount();
        return transactionRepository.createInternal(internal, balanceSender, balanceReceiver, senderId, receiverId);
    }

    @Override
    public Withdrawal createWithdrawal(TransactionDTO transactionDTO) {
        Withdrawal withdrawal = transactionMapper.createWithdrawal(transactionDTO);
        if (withdrawal.getSender().getBalance() - withdrawal.getAmount() < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
        int senderId = withdrawal.getSender().getId();
        checkIfWalletExists(senderId);
        int receiverId = withdrawal.getReceiver().getId();
        checkIfCardExists(receiverId);
        int balanceSender = withdrawal.getSender().getBalance() - withdrawal.getAmount();
        return transactionRepository.createWithdrawal(withdrawal, balanceSender, senderId);
    }

    private void checkIfCardExists(int id) {
        if (cardDetailsRepository.getById(id) == null) {
            throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
        }
    }

    private void checkIfWalletExists(int id) {
        if (walletRepository.getById(id) == null) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
        }
    }
}
