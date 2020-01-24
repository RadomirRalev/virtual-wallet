package com.example.demo.repositories;

import com.example.demo.models.transaction.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public TransactionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        try (Session session = sessionFactory.openSession()) {
            session.save(transaction);
        }
        return transaction;
    }
}
