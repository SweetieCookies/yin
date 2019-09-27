package com.qf.dao;

import com.qf.domain.Order;
import com.qf.domain.OrderDetail;

import java.util.List;

public interface OrderDao {
    void add(Order order);

    List<Order> findById(int uid);

    Order findByOid(String oid);

    List<OrderDetail> findOrderDetail(String oid);
}
