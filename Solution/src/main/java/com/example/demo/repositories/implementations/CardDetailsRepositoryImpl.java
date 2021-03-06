package com.example.demo.repositories.implementations;

import com.example.demo.models.card.CardDetails;
import com.example.demo.repositories.contracts.CardDetailsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                    " where id = :id and enabled = :status ", CardDetails.class);
            query.setParameter("id", id);
            query.setParameter("status", ENABLE);
            return query.list().get(0);
        }
    }

    @Override
    public CardDetails getByNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            Query<CardDetails> query = session.createQuery("from CardDetails " +
                    " where number = :number and enabled = :status ", CardDetails.class);
            query.setParameter("number", number);
            query.setParameter("status", ENABLE);
            return query.list().get(0);
        }
    }

    @Override
    public CardDetails createCard(CardDetails cardDetails) {
        try (Session session = sessionFactory.openSession()) {
            session.save(cardDetails);
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
    public void setCardStatus(String number, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update CardDetails " +
                    "set enabled = :status where number = :number ")
                    .setParameter("number", number)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public boolean checkIfCardNumberExists(String number) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CardDetails " +
                    " where number = :number ", CardDetails.class)
                    .setParameter("number", number)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean checkIfCardIdExists(int cardId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CardDetails " +
                    " where id = :cardId ", CardDetails.class)
                    .setParameter("cardId", cardId)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean checkIfCardIdValid(int cardId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CardDetails " +
                    " where id = :cardId and enabled = true ", CardDetails.class)
                    .setParameter("cardId", cardId)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isUserIsOwner(int cardId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from CardDetails " +
                    " where id = :cardId and user.id = :userId ", CardDetails.class)
                    .setParameter("cardId", cardId)
                    .setParameter("userId", userId)
                    .list().isEmpty();
        }
    }
}
