package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.wallet.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.CARD_WITH_ID_NOT_EXISTS;
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

}
