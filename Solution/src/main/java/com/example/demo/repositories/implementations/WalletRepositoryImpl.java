package com.example.demo.repositories.implementations;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.contracts.WalletRepository;
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
    public Wallet getWalletById(int walletId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet " +
                    " where id = :walletId", Wallet.class);
            query.setParameter("walletId", walletId);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, walletId);
            }
            return query.list().get(0);
        }
    }

    @Override
    public boolean checkIfWalletIdExists(int walletId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from Wallet " +
                    " where id = :walletId and is_deleted = :status", Wallet.class)
                    .setParameter("walletId", walletId)
                    .setParameter("status", DISABLE)
                    .list().isEmpty();
        }
    }

    @Override
    public List<Wallet> getWalletsbyUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("from Wallet " +
                    "where user_id = :userId and is_deleted = :status", Wallet.class);
            query.setParameter("status", DISABLE);
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
            Query<Wallet> query = session.createQuery("from Wallet " +
                    "where is_default = :status and user_id = :userid", Wallet.class);
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

    @Override
    public Wallet deleteWallet(Wallet walletToBeDeleted) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Wallet wallet = session.get(Wallet.class, walletToBeDeleted.getId());
            wallet.setIsWalletDeleted(ENABLE);
            session.saveOrUpdate(wallet);
            session.getTransaction().commit();
            return wallet;
        }
    }

    @Override
    public boolean checkIfBalanceIsPositive(int walletId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from Wallet " +
                    " where id = :walletId and is_deleted = :status and balance > 0", Wallet.class)
                    .setParameter("walletId", walletId)
                    .setParameter("status", DISABLE)
                    .list().isEmpty();
        }
    }
}
