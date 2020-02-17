package com.example.demo.repositories.contracts;

import com.example.demo.models.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionFilterRepository extends TransactionRepository {

    List<Transaction> getTransactionsByUserId(String direction, int userId, int page, String sort);

    List<Transaction> getTransactionsByUserId(String direction, String recipientSearchString, int userId, int page, String sort);

    List<Transaction> getTransactionsByUserId(String direction, LocalDate startDate, LocalDate endDate, int userId, int page, String sort);

    List<Transaction> getTransactionsByUserId(String direction, LocalDate startDate, LocalDate endDate, String recipientSearchString, int userId, int page, String sort);

}
