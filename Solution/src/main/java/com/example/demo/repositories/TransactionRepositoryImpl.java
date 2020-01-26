package com.example.demo.repositories;

import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Transaction> getTransactions() {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> query = session.createQuery("from Internal", Transaction.class);
            Query<Transaction> query1 = session.createQuery("from Deposit", Transaction.class);
            List<Transaction> result = new ArrayList<>();
            result.addAll(query.list());
            result.addAll(query1.list());
            return result;
        }
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> query = session.createQuery("from Internal where sender.id = :id", Transaction.class);
            Query<Transaction> query1 = session.createQuery("from Internal where receiver.id = :id", Transaction.class);
            Query<Transaction> query2 = session.createQuery("from Deposit where receiver.id = :id", Transaction.class);
            query.setParameter("id", id);
            query1.setParameter("id", id);
            query2.setParameter("id", id);
            List<Transaction> result = new ArrayList<>();
            result.addAll(query.list());
            result.addAll(query1.list());
            result.addAll(query2.list());
            return result;
        }
    }
}
