package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.wallet.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.CARD_WITH_ID_NOT_EXISTS;
import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    SessionFactory sessionFactory;

    @Autowired
    public WalletRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        try (Session session = sessionFactory.openSession()) {
            session.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet " +
                    " where id = :id", Wallet.class);
            query.setParameter("id", id);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
            }
            return query.list().get(0);
        }
    }

    @Override
    public boolean checkIfWalletIdExists(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet " +
                    " where id = :id", Wallet.class);
            query.setParameter("id", id);
            return query.list().isEmpty();
        }
    }

    @Override
    public List<Wallet> getWalletsbyUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet where user_id = :userId", Wallet.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    @Override
    public Wallet updateWallet(Wallet walletToBeUpdated) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(walletToBeUpdated);
            session.getTransaction().commit();
            return walletToBeUpdated;
        }
    }

    @Override
    public Wallet getDefaultWallet(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet where is_default = :status and user_id = :userid", Wallet.class);
            query.setParameter("status", ENABLE);
            query.setParameter("userid", userId);
            return query.list().get(0);
        }
    }

    @Override
    public Wallet disableDefaultWallet(int walletId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Wallet wallet = session.get(Wallet.class, walletId);
            wallet.setWalletDefault(DISABLE);
            session.saveOrUpdate(wallet);
            session.getTransaction().commit();
            return wallet;
        }
    }

    @Override
    public Wallet setAsDefault(Wallet walletToBeUpdated) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Wallet wallet = session.get(Wallet.class, walletToBeUpdated.getId());
            wallet.setWalletDefault(ENABLE);
            session.saveOrUpdate(wallet);
            session.getTransaction().commit();
            return wallet;
        }
    }
}
