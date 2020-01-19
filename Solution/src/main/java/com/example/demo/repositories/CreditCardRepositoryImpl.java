package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.creditcard.CreditCard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class CreditCardRepositoryImpl implements CreditCardRepository {

    SessionFactory sessionFactory;

    @Autowired
    public CreditCardRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CreditCard createCreditCard(CreditCard creditCard) {
        try (Session session = sessionFactory.openSession()) {
            session.save(creditCard);
        }
        return creditCard;
    }

    @Override
    public CreditCard updateCreditCard(CreditCard creditCard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(creditCard);
            session.getTransaction().commit();
        }
        return creditCard;
    }

    @Override
    public CreditCard getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<CreditCard> query = session.createQuery("from CreditCard " +
                    " where number = :number and status = :status ", CreditCard.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CREDIT_CARD_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public void setStatusCreditCard(String number, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update CreditCard " +
                    "set status = :status where number = :number ")
                    .setParameter("number", number)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public boolean creditCardExist(String number) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CreditCard " +
                    "where number = :number", CreditCard.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}
