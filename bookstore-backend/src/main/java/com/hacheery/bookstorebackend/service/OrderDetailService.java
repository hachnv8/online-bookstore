package com.hacheery.bookstorebackend.service;


import com.hacheery.bookstorebackend.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetail createOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> getOrderDetails();
    OrderDetail getOrderDetailById(Long orderDetailId);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
    void deleteOrderDetail(Long orderDetailId);
}
