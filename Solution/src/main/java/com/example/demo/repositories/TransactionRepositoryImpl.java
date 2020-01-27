package com.example.demo.repositories;

import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.transaction.Withdrawal;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import io.swagger.models.auth.In;
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
    public Internal createInternal(Internal internal, int balanceSender, int balanceReceiver, int senderId, int receiverId) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.save(internal);
                setSenderBalance(balanceSender, senderId, session);
                setReceiverBalance(balanceReceiver, receiverId, session);
                return internal;
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Withdrawal createWithdrawal(Withdrawal withdrawal, int balanceSender, int senderId) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.save(withdrawal);
                setSenderBalance(balanceSender, senderId, session);
                return withdrawal;
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Transaction> getTransactions() {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternal = session.createQuery("from Internal", Transaction.class);
            Query<Transaction> queryDeposit = session.createQuery("from Deposit", Transaction.class);
            Query<Transaction> queryWithdrawal = session.createQuery("from Withdrawal", Transaction.class);
            List<Transaction> result = new ArrayList<>();
            result.addAll(queryInternal.list());
            result.addAll(queryDeposit.list());
            result.addAll(queryWithdrawal.list());
            return result;
        }
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternalSender = session.createQuery("from Internal where sender.id = :id", Transaction.class);
            Query<Transaction> queryInternalReceiver = session.createQuery("from Internal where receiver.id = :id", Transaction.class);
            Query<Transaction> queryDepositReceiver = session.createQuery("from Deposit where receiver.id = :id", Transaction.class);
            Query<Transaction> queryWithdrawal = session.createQuery("from Withdrawal where sender.id = :id", Transaction.class);
            queryInternalSender.setParameter("id", id);
            queryInternalReceiver.setParameter("id", id);
            queryDepositReceiver.setParameter("id", id);
            queryWithdrawal.setParameter("id", id);
            List<Transaction> result = new ArrayList<>();
            result.addAll(queryInternalSender.list());
            result.addAll(queryInternalReceiver.list());
            result.addAll(queryDepositReceiver.list());
            result.addAll(queryWithdrawal.list());
            return result;
        }
    }

    private void setReceiverBalance(int balanceReceiver, int receiverId, Session session) {
        Query queryReceiver = session.createQuery("update Wallet set balance = :balanceReceiver where id = :receiverId");
        queryReceiver.setParameter("balanceReceiver", balanceReceiver);
        queryReceiver.setParameter("receiverId", receiverId);
        queryReceiver.executeUpdate();
    }

    private void setSenderBalance(int balanceSender, int senderId, Session session) {
        Query querySender = session.createQuery("update Wallet set balance = :balanceSender where id = :senderId");
        querySender.setParameter("balanceSender", balanceSender);
        querySender.setParameter("senderId", senderId);
        querySender.executeUpdate();
    }
}
