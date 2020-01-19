package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;

import com.example.demo.models.card.debitcard.DebitCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class DebitCardRepositoryImpl implements DebitCardRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public DebitCardRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public DebitCard createDebitCard(DebitCard debitCard) {
        try (Session session = sessionFactory.openSession()) {
            session.save(debitCard);
        }
        return debitCard;
    }

    @Override
    public DebitCard updateDebitCard(DebitCard debitCard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(debitCard);
            session.getTransaction().commit();
        }
        return debitCard;
    }

    @Override
    public DebitCard getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<DebitCard> query = session.createQuery("from DebitCard " +
                    " where number = :number and status = :status ", DebitCard.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(DEBIT_CARD_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public void setStatusDebitCard(String number, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update DebitCard " +
                    "set status = :status where number = :number ")
                    .setParameter("number", number)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public boolean debitCardExist(String number) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from DebitCard " +
                    "where number = :number", DebitCardRepository.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}
