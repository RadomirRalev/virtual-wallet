package com.example.demo.repositories;

import com.example.demo.models.role.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private SessionFactory sessionFactory;

    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role createRole(Role role) {
        try (Session session = sessionFactory.openSession()) {
            session.save(role);
        }
        return role;
    }


}
