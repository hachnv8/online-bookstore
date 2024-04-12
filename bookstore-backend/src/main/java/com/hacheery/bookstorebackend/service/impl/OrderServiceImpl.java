package com.hacheery.bookstorebackend.service.impl;

import com.hacheery.bookstorebackend.entity.Order;
import com.hacheery.bookstorebackend.exception.ResourceNotFoundException;
import com.hacheery.bookstorebackend.payload.request.OrderRequest;
import com.hacheery.bookstorebackend.repository.OrderRepository;
import com.hacheery.bookstorebackend.service.OrderService;
import com.hacheery.bookstorebackend.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Page<Order> getOrders(OrderRequest orderRequest, Pageable pageable) {
        Specification<Order> spec = OrderSpecification.searchByParameter(
                orderRequest
        );
        return orderRepository.findAll(spec, pageable);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay"));
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
