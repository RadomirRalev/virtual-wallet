package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;

import com.example.demo.models.card.physical.PhysicalCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class PhysicalCardRepositoryImpl implements PhysicalCardRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public PhysicalCardRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public PhysicalCard createPhysicalCard(PhysicalCard physicalCard) {
        try (Session session = sessionFactory.openSession()) {
            session.save(physicalCard);
        }
        return physicalCard;
    }

    @Override
    public PhysicalCard updatePhysicalCard(PhysicalCard physicalCard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(physicalCard);
            session.getTransaction().commit();
        }
        return physicalCard;
    }

    @Override
    public PhysicalCard getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<PhysicalCard> query = session.createQuery("from PhysicalCard " +
                    " where number = :number and status = :status ", PhysicalCard.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(DEBIT_CARD_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public void setStatusPhysicalCard(String number, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update PhysicalCard " +
                    "set status = :status where number = :number ")
                    .setParameter("number", number)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public boolean isPhysicalCardExist(String number) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from PhysicalCard " +
                    "where number = :number", PhysicalCardRepository.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}