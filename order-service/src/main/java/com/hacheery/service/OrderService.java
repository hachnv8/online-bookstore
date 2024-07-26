package com.hacheery.service;

import com.hacheery.enitity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(Long id);

    List<Order> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, String status);

    void deleteOrder(Long orderId);
    
    List<Order> findOrdersByStatus(String status);
}
