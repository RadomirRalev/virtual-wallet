package com.example.demo.repositories.implementations;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.repositories.contracts.VerificationTokenRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.constants.ExceptionConstants.TOKEN_NOT_FOUND;

@Repository
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public VerificationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

   public VerificationToken create(VerificationToken verificationToken) {
        try (Session session = sessionFactory.openSession()) {
            session.save(verificationToken);
        }
        return verificationToken;
    }

    @Override
    public VerificationToken getByToken(String token) {
        try (Session session = sessionFactory.openSession()) {
            Query<VerificationToken> query = session.createQuery("from VerificationToken " +
                    " where token = :token ", VerificationToken.class);
            query.setParameter("token", token);
            if (query.list().size() != 1) {
                throw new EntityNotFoundException(TOKEN_NOT_FOUND, token);
            }
            return query.list().get(0);
        }

    }

}
