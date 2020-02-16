package com.example.demo.repositories;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.constants.PaginationConstants.getPaginatedQueryResult;
import static com.example.demo.constants.SQLQueryConstants.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers(int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return getPaginatedQueryResult(page, query);
        }
    }

    @Override
    public List<User> getUsersForConfirm() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createNativeQuery(GET_USERS_FOR_CONFIRM, User.class);
            return query.list();
        }
    }

    @Override
    public User createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
        return user;
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    " where id = :id", User.class);
            query.setParameter("id", id);
            return query.list().get(0);
        }
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
    public void setStatusUser(String username, boolean status) {
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
    public void setBlockedStatus(String username, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update User " +
                    " set blocked = :status where username = :username ")
                    .setParameter("username", username)
                    .setParameter("status", status)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
        }
    }

    @Override
    public void setStatusIdentity(String username, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update User " +
                    " set confirm_identity = :status where username = :username ")
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
    public User changePassword(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public User updateNames(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public boolean isUsernameExist(String username) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where username = :username", User.class)
                    .setParameter("username", username)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where email = :email", User.class)
                    .setParameter("email", email)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isPhoneNumberExist(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where phoneNumber = :phoneNumber", User.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isIdentityConfirm(String username) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where confirm_identity = true and username = :username", User.class)
                    .setParameter("username", username)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isBlocked(String username) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where blocked = true and username = :username", User.class)
                    .setParameter("username", username)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isBlocked(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where blocked = true and id = :userId ", User.class)
                    .setParameter("userId", userId)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean isEnabled(String username) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where enabled = true and username = :username", User.class)
                    .setParameter("username", username)
                    .list().isEmpty();
        }
    }

    @Override
    public boolean checkIfUserIdExists(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return !session.createQuery("from User " +
                    " where id = :userId", User.class)
                    .setParameter("userId", userId)
                    .list().isEmpty();
        }
    }

    @Override
    public List<User> searchByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username like :username and blocked =  false ");
            query.setParameter("username", "%" + username + "%");
            return query.list();
        }
    }

    @Override
    public List<User> searchByPhoneNumber(String phoneNum) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where phoneNumber like :phoneNum and blocked=false ");
            query.setParameter("phoneNum", phoneNum);
            return query.list();
        }
    }

    @Override
    public List<User> searchByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email like :email and blocked=false ");
            query.setParameter("email", "%" + email + "%");
            return query.list();
        }

    }

    @Override
    public List<User> searchByUsernameAsAdmin(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username like :username ");
            query.setParameter("username", "%" + username + "%");
            return query.list();
        }
    }

    @Override
    public List<User> searchByPhoneNumberAsAdmin(String phoneNum) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where phoneNumber like :phoneNum ");
            query.setParameter("phoneNum", phoneNum);
            return query.list();
        }
    }

    @Override
    public List<User> searchByEmailAsAdmin(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email like :email  ");
            query.setParameter("email", "%" + email + "%");
            return query.list();
        }
    }
}
