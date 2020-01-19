package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return query.list();
        }
    }

    @Override
    public User createUser(User user, Role role) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.save(role);
            String sql = String.format(ADD_USER_ROLE, user.getId(), role.getId());
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    " where username = :username and enabled = :status ", User.class);
            query.setParameter("username", username);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(USER_USERNAME_NOT_FOUND, username);
            }
            return query.list().get(0);
        }
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    " where phoneNumber = :phoneNumber and enabled = :status ", User.class);
            query.setParameter("phoneNumber", phoneNumber);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(USER_PHONE_NUMBER_NOT_FOUND, phoneNumber);
            }
            return query.list().get(0);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    " where email = :email and enabled = :status ", User.class);
            query.setParameter("email", email);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(USER_EMAIL_NOT_FOUND, email);
            }
            return query.list().get(0);
        }
    }

    @Override
    public void setStatusUser(String username, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update User " +
                    " set enabled = :status where username = :username ")
                    .setParameter("username", username)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public User updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public boolean usernameExist(String username) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where username = :username", User.class)
                    .setParameter("username", username)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean emailExist(String email) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where email = :email", User.class)
                    .setParameter("email", email)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean phoneNumberExist(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .list().isEmpty();
        }
    }
}
