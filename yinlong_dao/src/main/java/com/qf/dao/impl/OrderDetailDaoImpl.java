package com.qf.dao.impl;

import com.qf.dao.OrderDetailDao;
import com.qf.domain.OrderDetail;
import com.qf.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public void add(OrderDetail order) {
        QueryRunner qr=new QueryRunner();
        Object[] params={order.getOid(),order.getPid(),order.getNum(),order.getMoney()};
        try {
            qr.update(DataSourceUtils.getConnection(), "insert into tb_orderdetail (oid,pid,num,money) values(?,?,?,?)", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败", e);
        }
    }
}
