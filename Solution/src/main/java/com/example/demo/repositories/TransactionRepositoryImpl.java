package com.example.demo.repositories;

import com.example.demo.models.transaction.Deposit;
import com.example.demo.models.transaction.Internal;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.Withdrawal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constants.PaginationConstants.getPaginatedResult;
import static com.example.demo.constants.SQLQueryConstants.*;
import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Repository
@Transactional
public class TransactionRepositoryImpl implements TransactionRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public TransactionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Internal createInternal(Internal internal, double balanceSender, double balanceReceiver, int senderId, int receiverId) {
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
    public Withdrawal createWithdrawal(Withdrawal withdrawal, double balanceSender, int senderId) {
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
    public Deposit createDeposit(Deposit deposit, double balanceReceiver, int receiverId) {
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
    public List<Transaction> getTransactions(int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL, Transaction.class);
            Query<Transaction> queryDeposit = session.createQuery(FROM_DEPOSIT, Transaction.class);
            Query<Transaction> queryWithdrawal = session.createQuery(FROM_WITHDRAWAL, Transaction.class);

            return getTransactions(queryInternal, queryDeposit, queryWithdrawal, page);
        }
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int walletId, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternalSender = session.createQuery(FROM_INTERNAL_SENDERID_WALLET, Transaction.class);
            queryInternalSender.setParameter("walletId", walletId);
            Query<Transaction> queryInternalReceiver = session.createQuery(FROM_INTERNAL_RECEIVERID_WALLET, Transaction.class);
            queryInternalReceiver.setParameter("walletId", walletId);
            Query<Transaction> queryDepositReceiver = session.createQuery(FROM_WITHDRAWAL_DEPOSIT_WALLET, Transaction.class);
            queryDepositReceiver.setParameter("walletId", walletId);
            Query<Transaction> queryWithdrawal = session.createQuery(FROM_WITHDRAWAL_SENDERID_WALLET, Transaction.class);
            queryWithdrawal.setParameter("walletId", walletId);
            return getTransactions(queryInternalSender, queryInternalReceiver, queryDepositReceiver, queryWithdrawal, page);
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> queryInternalSender = session.createQuery(SELECT_SENDER_INTERNAL_SENDERID, Transaction.class);
            queryInternalSender.setParameter("userId", userId);
            Query<Transaction> queryInternalReceiver = session.createQuery(SELECT_RECEIVER_INTERNAL_RECEIVERID, Transaction.class);
            queryInternalReceiver.setParameter("userId", userId);
            Query<Transaction> queryDepositReceiver = session.createQuery(SELECT_RECEIVER_DEPOSIT_RECEIVERID, Transaction.class);
            queryDepositReceiver.setParameter("userId", userId);
            Query<Transaction> queryWithdrawal = session.createQuery(SELECT_SENDER_WITHDRAWAL_SENDERID, Transaction.class);
            queryWithdrawal.setParameter("userId", userId);
            return getTransactions(queryInternalSender, queryInternalReceiver, queryDepositReceiver, queryWithdrawal, page);
        }
    }

    @Override
    public boolean checkIfIdempotencyKeyExists(String idempotencyKey) {
        try (Session session = sessionFactory.openSession()) {
            List<Transaction> result = new ArrayList<>();
            Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL_KEY, Transaction.class);
            queryInternal.setParameter("idempotencyKey", idempotencyKey);
            result.addAll(queryInternal.list());
            Query<Transaction> queryDeposit = session.createQuery(FROM_DEPOSIT_KEY, Transaction.class);
            queryDeposit.setParameter("idempotencyKey", idempotencyKey);
            result.addAll(queryDeposit.list());
            Query<Transaction> queryWithdrawal = session.createQuery(FROM_WITHDRAWAL_KEY, Transaction.class);
            queryWithdrawal.setParameter("idempotencyKey", idempotencyKey);
            result.addAll(queryWithdrawal.list());
            return !result.isEmpty();
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String direction, LocalDate startDate, LocalDate endDate, int userId, int page) {
        try (Session session = sessionFactory.openSession()) {
            List<Transaction> result = new ArrayList<>();
            if (direction.equalsIgnoreCase("All") || direction.equalsIgnoreCase("Outgoing")) {
                getQueryStartDateEndDateUserId(startDate, endDate, userId, session, result, FROM_INTERNAL_DATE_SENDERID);
                getQueryStartDateEndDateUserId(startDate, endDate, userId, session, result, FROM_WITHDRAWAL_DATE_SENDERID);
            }
            if (direction.equalsIgnoreCase("All") || direction.equalsIgnoreCase("Incoming")) {
                getQueryStartDateEndDateUserId(startDate, endDate, userId, session, result, FROM_INTERNAL_DATE_RECEIVERID);
                getQueryStartDateEndDateUserId(startDate, endDate, userId, session, result, FROM_DEPOSIT_DATE_RECEIVERID);
            }
            return getPaginatedResult(page, result);
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String direction, String recipientSearchString, int userId, int page) {
        try (Session session = sessionFactory.openSession()) {
            List<Transaction> result = new ArrayList<>();
            if (direction.equalsIgnoreCase("All")) {
                if (!recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                    getQueryRecipientUserId(recipientSearchString, userId, session, result);
                } else {
                    getQueryRecipient(recipientSearchString, session, result);
                    getQueryUserId(userId, session, result);
                }
            }
            if (direction.equalsIgnoreCase("Incoming") && recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipient(recipientSearchString, session, result);
                getQueryUserId(userId, session, result);
            }
            if (direction.equalsIgnoreCase("Outgoing") && !recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipientUserId(recipientSearchString, userId, session, result);
            }

            if (direction.equalsIgnoreCase("Outgoing") && recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipientUserId(recipientSearchString, userId, session, result);
            }
            return getPaginatedResult(page, result);
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String direction, LocalDate startDate, LocalDate endDate, String recipientSearchString, int userId, int page) {
        try (Session session = sessionFactory.openSession()) {
            List<Transaction> result = new ArrayList<>();
            if (direction.equalsIgnoreCase("All")) {
                if (!recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                    getQueryRecipientUserIdDate(startDate, endDate, recipientSearchString, userId, session, result);
                } else {
                    getQueryRecipientDate(startDate, endDate, recipientSearchString, session, result);
                    getQueryUserIdDate(startDate, endDate, userId, session, result);
                }
            }
            if (direction.equalsIgnoreCase("Incoming") && recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipientDate(startDate, endDate, recipientSearchString, session, result);
                getQueryUserIdDate(startDate, endDate, userId, session, result);
            }
            if (direction.equalsIgnoreCase("Outgoing") && !recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipientUserIdDate(startDate, endDate, recipientSearchString, userId, session, result);
            }

            if (direction.equalsIgnoreCase("Outgoing") && recipientSearchString.equalsIgnoreCase(currentPrincipalName())) {
                getQueryRecipientUserIdDate(startDate, endDate, recipientSearchString, userId, session, result);
            }
            return getPaginatedResult(page, result);
        }
    }

    private void getQueryUserId(int userId, Session session, List<Transaction> result) {
        Query<Transaction> queryDeposit = session.createQuery(FROM_DEPOSIT_RECEIVERID, Transaction.class);
        queryDeposit.setParameter("userId", userId);
        result.addAll(queryDeposit.list());
    }

    private void getQueryRecipientUserId(String recipientSearchString, int userId, Session session, List<Transaction> result) {
        Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL_RECEIVERNAME_SENDERID, Transaction.class);
        queryInternal.setParameter("recipientSearchString", recipientSearchString);
        queryInternal.setParameter("userId", userId);
        result.addAll(queryInternal.list());
    }

    private void getQueryRecipient(String recipientSearchString, Session session, List<Transaction> result) {
        Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL_RECEIVERNAME, Transaction.class);
        queryInternal.setParameter("recipientSearchString", recipientSearchString);
        result.addAll(queryInternal.list());
    }

    private void getQueryStartDateEndDateUserId(LocalDate startDate, LocalDate endDate, int userId, Session session, List<Transaction> result, String fromInternalDateSenderid) {
        Query<Transaction> query = session.createQuery(fromInternalDateSenderid, Transaction.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("userId", userId);
        result.addAll(query.list());
    }

    private void getQueryUserIdDate(LocalDate startDate, LocalDate endDate, int userId, Session session, List<Transaction> result) {
        Query<Transaction> queryDeposit = session.createQuery(FROM_DEPOSIT_DATE_RECEIVERID, Transaction.class);
        queryDeposit.setParameter("userId", userId);
        queryDeposit.setParameter("startDate", startDate);
        queryDeposit.setParameter("endDate", endDate);
        result.addAll(queryDeposit.list());
    }

    private void getQueryRecipientDate(LocalDate startDate, LocalDate endDate, String recipientSearchString, Session session, List<Transaction> result) {
        Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL_DATE_RECEIVERNAME, Transaction.class);
        queryInternal.setParameter("recipientSearchString", recipientSearchString);
        queryInternal.setParameter("startDate", startDate);
        queryInternal.setParameter("endDate", endDate);
        result.addAll(queryInternal.list());
    }

    private void getQueryRecipientUserIdDate(LocalDate startDate, LocalDate endDate, String recipientSearchString, int userId, Session session, List<Transaction> result) {
        Query<Transaction> queryInternal = session.createQuery(FROM_INTERNAL_DATE_RECEIVERNAME_SENDERID, Transaction.class);
        queryInternal.setParameter("recipientSearchString", recipientSearchString);
        queryInternal.setParameter("userId", userId);
        queryInternal.setParameter("startDate", startDate);
        queryInternal.setParameter("endDate", endDate);
        result.addAll(queryInternal.list());
    }

    private void setBalance(double balance, int id, Session session) {
        org.hibernate.Transaction txn = session.beginTransaction();
        Query query = session.createQuery("update Wallet set balance = :balance where id = :id");
        query.setParameter("balance", balance);
        query.setParameter("id", id);
        query.executeUpdate();
        txn.commit();
    }

    private List<Transaction> getTransactions(Query<Transaction> queryInternalSender) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(queryInternalSender.list());
        return result;
    }

    private List<Transaction> getTransactions(Query<Transaction> queryInternalSender, Query<Transaction> queryInternalReceiver, Query<Transaction> queryDepositReceiver, Query<Transaction> queryWithdrawal, int page) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(queryInternalSender.list());
        result.addAll(queryInternalReceiver.list());
        result.addAll(queryDepositReceiver.list());
        result.addAll(queryWithdrawal.list());
        return getPaginatedResult(page, result);
    }

    private List<Transaction> getTransactions(Query<Transaction> queryInternal, Query<Transaction> queryDeposit, Query<Transaction> queryWithdrawal, int page) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(queryInternal.list());
        result.addAll(queryDeposit.list());
        result.addAll(queryWithdrawal.list());
        return getPaginatedResult(page, result);
    }
}

