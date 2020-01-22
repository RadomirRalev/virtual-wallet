package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class CardDetailsRepositoryImpl implements CardDetailsRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public CardDetailsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CardDetails getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<CardDetails> query = session.createQuery("from CardDetails " +
                    " where id = :id and status = :status ", CardDetails.class);
            query.setParameter("id", id);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
            }
            return query.list().get(0);
        }
    }

    @Override
    public CardDetails getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<CardDetails> query = session.createQuery("from CardDetails " +
                    " where number = :number and status = :status ", CardDetails.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS, number);
            }
            return query.list().get(0);
        }
    }

    @Override
    public CardDetails createCard(CardDetails cardDetails, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(cardDetails);
            String sql = String.format(ADD_USER_CARD, user.getId(), cardDetails.getId());
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
        return cardDetails;
    }

    @Override
    public CardDetails updateCard(CardDetails cardDetails) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(cardDetails);
            session.getTransaction().commit();
        }
        return cardDetails;
    }

    @Override
    public void setCardStatus(String number, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update CardDetails " +
                    "set status = :status where number = :number ")
                    .setParameter("number", number)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public boolean isCardExist(String number) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CardDetails " +
                    " where number = :number ", CardDetails.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }
}
