package com.example.demo.services;

import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsbyWalletId(int id);

    Internal createInternal(TransactionDTO transactionDTO);

    Withdrawal createWithdrawal(TransactionDTO transactionDTO);

    Deposit createDeposit(TransactionDTO transactionDTO);

    boolean checkIfIdempotencyKeyExists(String idempotencyKey);

    boolean checkIfWalletIdExists(int id);

    Withdrawal getWithdrawal(TransactionDTO transactionDTO);

    Internal getInternal(TransactionDTO transactionDTO);

    Deposit getDeposit(TransactionDTO transactionDTO);

    void checkIfFundsAreEnough(Wallet sender, double amount);

    List<Transaction> getTransactionsByUserId(int userId);

    List<Transaction> getFilteredTransactions(String direction, String startDate, String endDate, String recipientSearchString, int userId);

    List<Transaction> getTransactionsByDate(String direction, String startDate, String endDate, int userId);

    List<Transaction> getTransactionsByRecipient(String direction, String recipientSearchString, int userId);
}
