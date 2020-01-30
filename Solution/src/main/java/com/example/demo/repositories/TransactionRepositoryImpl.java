package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.transaction.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.WALLET_WITH_ID_NOT_EXISTS;

@Repository
@Transactional
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
                setBalance(balanceSender, senderId, session);
                setBalance(balanceReceiver, receiverId, session);
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
                setBalance(balanceSender, senderId, session);
                return withdrawal;
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Deposit createDeposit(Deposit deposit, int balanceReceiver, int receiverId) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.save(deposit);
                setBalance(balanceReceiver, receiverId, session);
                return deposit;
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

    @Override
    public boolean checkIfIdempotencyKeyExists(String idempotencyKey) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternal = session.createQuery("from Internal " +
                    " where idempotencyKey = :idempotencyKey ", Transaction.class);
            Query<Transaction> queryDeposit = session.createQuery("from Deposit" +
                    " where idempotencyKey = :idempotencyKey ", Transaction.class);
            Query<Transaction> queryWithdrawal = session.createQuery("from Withdrawal " +
                    " where idempotencyKey = :idempotencyKey ", Transaction.class);
            queryInternal.setParameter("idempotencyKey", idempotencyKey);
            queryDeposit.setParameter("idempotencyKey", idempotencyKey);
            queryWithdrawal.setParameter("idempotencyKey", idempotencyKey);
            List<Transaction> result = new ArrayList<>();
            result.addAll(queryInternal.list());
            result.addAll(queryDeposit.list());
            result.addAll(queryWithdrawal.list());
            return !result.isEmpty();
        }
    }

    private void setBalance(int balance, int id, Session session) {
        org.hibernate.Transaction txn = session.beginTransaction();
        Query query = session.createQuery("update Wallet set balance = :balance where id = :id");
        query.setParameter("balance", balance);
        query.setParameter("id", id);
        query.executeUpdate();
        txn.commit();
    }
}
