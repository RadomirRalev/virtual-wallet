package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.USER_ID_NOT_FOUND;

@Repository
public class ConfirmIdentityRepositoryImpl implements ConfirmIdentityRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ConfirmIdentityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ConfirmIdentity createConfrimIdentity(ConfirmIdentity confirmIdentity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(confirmIdentity);
        }
        return confirmIdentity;
    }

    @Override
    public ConfirmIdentity getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<ConfirmIdentity> query = session.createQuery("from ConfirmIdentity " +
                    " where userId = :userId ", ConfirmIdentity.class);
            query.setParameter("userId", userId);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(USER_ID_NOT_FOUND, userId);
            }
            return query.list().get(0);
        }
    }

    public boolean isUserHaveConfirmIdentity(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from ConfirmIdentity " +
                    " where userId = :userId and dataOk = :dataOk ", ConfirmIdentity.class)
                    .setParameter("userId", userId)
                    .setParameter("dataOk", false)
                    .list().isEmpty();
        }
    }
}
