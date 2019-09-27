package com.qf.service;

import com.qf.domain.Order;
import com.qf.domain.OrderDetail;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order, List<OrderDetail> orderDetails);

    List<Order> orderQuery(int uid);

    Order orderDetailQuery(String oid);
}
