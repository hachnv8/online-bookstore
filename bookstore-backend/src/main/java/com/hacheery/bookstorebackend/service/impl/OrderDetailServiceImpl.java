package com.hacheery.bookstorebackend.service.impl;

import com.hacheery.bookstorebackend.entity.OrderDetail;
import com.hacheery.bookstorebackend.exception.SQLException;
import com.hacheery.bookstorebackend.repository.OrderDetailRepository;
import com.hacheery.bookstorebackend.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        Objects.requireNonNull(orderDetail, "Thông tin order không được để trống");
        try {
            return orderDetailRepository.save(orderDetail);
        } catch (DataIntegrityViolationException e) {
            throw new SQLException("Lỗi lưu danh mục vào cơ sở dữ liệu", e);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetails() {
        return null;
    }

    @Override
    public OrderDetail getOrderDetailById(Long orderDetailId) {
        return null;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return null;
    }

    @Override
    public void deleteOrderDetail(Long orderDetailId) {

    }
}
