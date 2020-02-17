package com.example.demo.services;

import com.example.demo.models.transaction.*;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions(int page);

    List<Transaction> getTransactionsbyWalletId(int id, int page);

    List<Transaction> getTransactionsByUserId(int userId, int page);

    Internal createInternal(TransactionDTO transactionDTO, String sender);

    Withdrawal createWithdrawal(TransactionDTO transactionDTO, String sender);

    Deposit createDeposit(TransactionDTO transactionDTO, String sender);

    Withdrawal getWithdrawal(TransactionDTO transactionDTO);

    Internal getInternal(TransactionDTO transactionDTO);

    Deposit getDeposit(TransactionDTO transactionDTO);

    List<Transaction> getFilteredTransactions(String direction, String startDate, String endDate, String recipientSearchString, int userId, int page);

    List<Transaction> getFilteredTransactionsAdmin(String direction, String startDate, String endDate,String senderSearchString, String recipientSearchString, int userId, int page);

    List<Transaction> sortTransactions(List<Transaction> filteredTransactions, String sort);
}
