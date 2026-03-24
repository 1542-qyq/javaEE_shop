package com.ch.ch9.repository.admin;

import com.ch.ch9.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminOrderRepository")
public interface OrderRepository {
    List<Order> findAll();
    List<Order> findByBusertableId(Integer busertableId);
    Order findById(Integer id);
    int add(Order order);
    int update(Order order);
    int delete(Integer id);
    void resetAutoIncrement();
}