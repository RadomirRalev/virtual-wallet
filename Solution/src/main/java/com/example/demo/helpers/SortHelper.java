package com.example.demo.helpers;

import com.example.demo.models.transaction.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortHelper {
    public static List<Transaction> sortTransactions(List<Transaction> filteredTransactions, String sort) {
        if (sort.equalsIgnoreCase("AmountAsc")) {
            return sortTransactionList(filteredTransactions, Comparator.comparing(Transaction::getAmount));
        }
        if (sort.equalsIgnoreCase("AmountDes")) {
            return sortTransactionList(filteredTransactions, Comparator.comparing(Transaction::getAmount)
                    .reversed());
        }
        if (sort.equalsIgnoreCase("DateAsc")) {
            return sortTransactionList(filteredTransactions, Comparator.comparing(Transaction::getDate));
        }
        if (sort.equalsIgnoreCase("DateDes")) {
            return sortTransactionList(filteredTransactions, Comparator.comparing(Transaction::getDate)
                    .reversed());
        }
        return filteredTransactions;
    }

    private static List<Transaction> sortTransactionList(List<Transaction> filteredTransactions, Comparator<Transaction> comparing) {
        return filteredTransactions.stream()
                .sorted(comparing)
                .collect(Collectors.toList());
    }
}
