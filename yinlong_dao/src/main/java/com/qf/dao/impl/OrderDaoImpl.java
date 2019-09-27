package com.qf.dao.impl;

import com.qf.dao.OrderDao;
import com.qf.domain.Order;
import com.qf.domain.OrderDetail;
import com.qf.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void add(Order order) {
        QueryRunner qr = new QueryRunner();
        Object[] params = {order.getId(), order.getUid(), order.getMoney(), order.getStatus(), order.getTime(), order.getAid()};
        try {
            qr.update(DataSourceUtils.getConnection(), "insert into tb_order (id,uid,money,status,time,aid) values(?,?,?,?,?,?)", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败", e);
        }
    }

    @Override
    public List<Order> findById(int uid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_order where uid=?", new BeanListHandler<>(Order.class), uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单失败", e);
        }
    }

    @Override
    public Order findByOid(String oid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_order where id=?", new BeanHandler<>(Order.class), oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单失败", e);
        }
    }

    @Override
    public List<OrderDetail> findOrderDetail(String oid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_orderdetail where oid=?", new BeanListHandler<>(OrderDetail.class), oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单详情失败", e);
        }
    }
}
