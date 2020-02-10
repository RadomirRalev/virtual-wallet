package com.example.demo.repositories;

import com.example.demo.models.transaction.Deposit;
import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.Withdrawal;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> getTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    Internal createInternal(Internal internal, double balanceSender, double balanceReceiver, int senderId, int receiverId);

    Withdrawal createWithdrawal(Withdrawal withdrawal, double balanceSender, int senderId);

    Deposit createDeposit(Deposit deposit, double balanceReceiver, int receiverId);

    boolean checkIfIdempotencyKeyExists(String idempotencyKey);

    List<Transaction> getTransactionsByUserId(int userId);

    List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate, int userId);

}
