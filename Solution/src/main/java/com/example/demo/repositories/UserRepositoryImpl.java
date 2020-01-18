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

import static com.example.demo.constants.ExceptionConstants.USER_PHONE_NUMBER_NOT_FOUND;
import static com.example.demo.constants.ExceptionConstants.USER_USERNAME_NOT_FOUND;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;
import static com.example.demo.constants.SQLQueryConstants.INSERT_USER_ROLE_SQL;

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
    public User createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            String sql = String.format(INSERT_USER_ROLE_SQL, user.getUsername());
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User getByUsername(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User" +
                    " where username = :name and enabled = :status ", User.class);
            query.setParameter("name", name);
            query.setParameter("status", ENABLE);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(USER_USERNAME_NOT_FOUND, name);
            }
            return query.list().get(0);
        }
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User" +
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
    public void setStatusUser(String username, int status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update User " +
                    "set enabled = :status where username = :username ")
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
    public boolean usernameExist(String name) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    "where username = :name", User.class)
                    .setParameter("name", name)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean emailExist(String email) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    "where email = :email", User.class)
                    .setParameter("email", email)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean phoneNumberExist(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    "where phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .list().isEmpty();
        }
    }
}
