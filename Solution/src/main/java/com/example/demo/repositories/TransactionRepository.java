package com.example.demo.repositories;

import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.Withdrawal;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> getTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    Internal createInternal(Internal internal, int balanceSender, int balanceReceiver, int senderId, int receiverId);

    Withdrawal createWithdrawal(Withdrawal withdrawal, int balanceSender, int senderId);

}
