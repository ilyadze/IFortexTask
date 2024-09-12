package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class IUserService implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        final String QUERY = "SELECT u FROM User u, Order o WHERE o.userId = u.id AND YEAR(o.createdAt) = ?1 AND o.orderStatus = 'DELIVERED' GROUP BY u.id ORDER BY SUM(o.price * o.quantity) DESC";
        TypedQuery<User> query = entityManager.createQuery(QUERY, User.class);
        final int USER_YEAR = 2003;
        return query.setParameter(1, USER_YEAR).getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<User> findUsers() {
        final String QUERY = "SELECT u FROM User u, Order o WHERE o.userId = u.id AND o.orderStatus = 'PAID' AND YEAR(o.createdAt) = ?1 GROUP BY u.id";
        TypedQuery<User> query = entityManager.createQuery(QUERY, User.class);
        final int USERS_YEAR = 2010;
        return query.setParameter(1, USERS_YEAR).getResultList();
    }
}
