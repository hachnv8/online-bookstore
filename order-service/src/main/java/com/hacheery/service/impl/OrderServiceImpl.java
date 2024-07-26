package com.hacheery.service.impl;

import com.hacheery.enitity.Order;
import com.hacheery.enitity.OrderItem;
import com.hacheery.model.Product;
import com.hacheery.repository.OrderRepository;
import com.hacheery.service.OrderService;
import com.hacheery.service.client.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductServiceClient productServiceClient;
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        // Kiểm tra tính hợp lệ của đơn hàng
        // ...

        // Lặp qua danh sách OrderItem để kiểm tra tồn kho
        for (OrderItem item : order.getOrderItems()) {
            Product product = productServiceClient.getProductById(item.getProductId());
            if (product == null || product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Đơn hàng không tồn tại");
            }
        }

        // Cập nhật số lượng tồn kho (có thể sử dụng Kafka để gửi thông báo cập nhật)
        // ...

        // Lưu đơn hàng vào database
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        order.setStatus(status);
        orderRepository.save(order);

        // Gửi thông báo cập nhật trạng thái đơn hàng (ví dụ: gửi email)
        // ...
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> findOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
