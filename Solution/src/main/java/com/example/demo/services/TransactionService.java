package com.example.demo.services;

import com.example.demo.models.transaction.*;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    List<Transaction> getTransactionsByUserId(int userId);

    Internal createInternal(TransactionDTO transactionDTO);

    Withdrawal createWithdrawal(TransactionDTO transactionDTO);

    Deposit createDeposit(TransactionDTO transactionDTO);

    Withdrawal getWithdrawal(TransactionDTO transactionDTO);

    Internal getInternal(TransactionDTO transactionDTO);

    Deposit getDeposit(TransactionDTO transactionDTO);

    List<Transaction> getFilteredTransactions(String direction, String startDate, String endDate, String recipientSearchString, int userId);

    List<Transaction> sortTransactions(List<Transaction> filteredTransactions, String sort);
}
