package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.virtual.VirtualCard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class VirtualCardRepositoryImpl implements VirtualCardRepository {

    SessionFactory sessionFactory;

    @Autowired
    public VirtualCardRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public VirtualCard createCreditCard(VirtualCard virtualCard) {
        try (Session session = sessionFactory.openSession()) {
            session.save(virtualCard);
        }
        return virtualCard;
    }

    @Override
    public VirtualCard updateCreditCard(VirtualCard virtualCard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(virtualCard);
            session.getTransaction().commit();
        }
        return virtualCard;
    }

    @Override
    public VirtualCard getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<VirtualCard> query = session.createQuery("from VirtualCard " +
                    " where number = :number and status = :status ", VirtualCard.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public void setStatusCreditCard(String number, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update VirtualCard " +
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
            return !session.createQuery("from VirtualCard " +
                    "where number = :number", VirtualCard.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}
