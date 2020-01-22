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
    public PhysicalCard getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<PhysicalCard> query = session.createQuery("from PhysicalCard " +
                    " where id = :id and status = :status ", PhysicalCard.class);
            query.setParameter("id", id);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
            }
            return query.list().get(0);
        }
    }

    @Override
    public PhysicalCard getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<PhysicalCard> query = session.createQuery("from PhysicalCard " +
                    " where number = :number and status = :status ", PhysicalCard.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public PhysicalCard createPhysicalCard(PhysicalCard physicalCard, int userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(physicalCard);
            String sql = String.format(ADD_USER_PHYSICAL_CARD, userId, physicalCard.getId());
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
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
                    " where number = :number ", PhysicalCard.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}
