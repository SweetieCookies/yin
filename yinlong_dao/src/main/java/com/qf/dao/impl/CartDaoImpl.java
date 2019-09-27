package com.qf.dao.impl;

import com.qf.dao.CartDao;
import com.qf.domain.Cart;
import com.qf.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CartDaoImpl implements CartDao {
    @Override
    public Cart findByUidAndGid(Integer id, int parseInt) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_cart where id=? and pid=? ",new BeanHandler<>(Cart.class),id,parseInt);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询购物车失败", e);
        }
    }

    @Override
    public void add(Cart cart) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        Object[] params={cart.getId(),cart.getPid(),cart.getNum(),cart.getMoney()};
        try {
            qr.update("insert into tb_cart (id,pid,num,money) values (?,?,?,?)", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("添加购物车失败", e);
        }

    }
    public  void update(Cart cart){
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        Object[] params={cart.getNum(),cart.getMoney(),cart.getId(),cart.getPid()};
        try {
            qr.update("update tb_cart set num=?,money=? where id=? and pid=?", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("更新购物车失败", e);
        }
    }

    @Override
    public List<Cart> findByUid(int uid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_cart where id=?",new BeanListHandler<>(Cart.class) ,uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("查询购物车失败", e);
        }
    }

    @Override
    public void delete(int uid, int pid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=? and pid =?", uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("删除物品失败", e);
        }
    }

    @Override
    public void deleteByUid(int uid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=?", uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("清空购物车失败", e);
        }
    }
}
