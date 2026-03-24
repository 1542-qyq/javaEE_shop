package com.ch.ch9.service.admin;

import com.ch.ch9.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAll();
    List<Order> findByBusertableId(Integer busertableId);
    Order findById(Integer id);
    void add(Order order) throws Exception;
    void update(Order order) throws Exception;
    void delete(Integer id) throws Exception;

    // 可选添加的辅助方法
    String checkServiceStatus();
    int countAllOrders();
    int countByStatus(String status);
}