package com.example.demo.repositories.implementations;

import com.example.demo.models.role.Role;
import com.example.demo.repositories.contracts.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private SessionFactory sessionFactory;

    @Autowired
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
