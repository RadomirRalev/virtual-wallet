package com.example.demo.repositories.contracts;

import com.example.demo.models.transaction.Deposit;
import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.Withdrawal;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> getTransactions(int page);

    List<Transaction> getTransactionsbyWalletId(int id, int page);

    Internal createInternal(Internal betweenWallets, double balanceSender, double balanceReceiver, int senderId, int receiverId);

    Withdrawal createWithdrawal(Withdrawal withdrawal, double balanceSender, int senderId);

    Deposit createDeposit(Deposit deposit, double balanceReceiver, int receiverId);

    List<Transaction> getTransactionsByUserId(int userId, int page);

    boolean checkIfIdempotencyKeyExists(String idempotencyKey);
}
