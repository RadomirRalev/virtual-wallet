package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.*;

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
    public ConfirmIdentity getByUserIdRequestForConfirm(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<ConfirmIdentity> query = session.createQuery("from ConfirmIdentity " +
                    " where userId = :userId and haveRequest = true ", ConfirmIdentity.class);
            query.setParameter("userId", userId);
            if (query.list().isEmpty()) {
                throw new EntityNotFoundException(USER_CONFIRM_IDENTITY_NOT_FOUND, userId);
            }
            return query.list().get(0);
        }
    }

    public void setStatus(int userId, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update ConfirmIdentity " +
                    " set haveRequest = :status where userId = :userId ")
                    .setParameter("userId", userId)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    public boolean isUserHaveConfirmIdentityRequest(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from ConfirmIdentity " +
                    " where userId = :userId and haveRequest = true ", ConfirmIdentity.class)
                    .setParameter("userId", userId)
                    .list().isEmpty();
        }
    }
}
