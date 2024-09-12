package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class IOrderService implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findOrder() {
        final String QUERY = "SELECT o FROM Order o WHERE o.quantity > 1 ORDER BY o.createdAt DESC";
        TypedQuery<Order> query = entityManager.createQuery(QUERY, Order.class);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Order> findOrders() {
        final String QUERY = "SELECT o FROM Order o, User u WHERE o.userId = u.id AND u.userStatus = 'ACTIVE' ORDER BY o.createdAt ASC";
        TypedQuery<Order> query = entityManager.createQuery(QUERY, Order.class);
        return query.getResultList();
    }
}
