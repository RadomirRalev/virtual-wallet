package com.example.demo.repositories;

import com.example.demo.models.wallet.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
