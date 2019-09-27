package com.qf.dao.impl;

import com.qf.dao.AddressDao;
import com.qf.domain.Address;
import com.qf.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class addressDaoImpl implements AddressDao {
    @Override
    public List<Address> findByUid(int uid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_address where uid=?", new BeanListHandler<Address>(Address.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询地址失败", e);
        }
    }

    @Override
    public void add(Address address) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        Object[] params={address.getDetail(),address.getName(),address.getPhone(),address.getUid(),address.getLevel()};
        try {
            qr.update("insert into tb_address (detail,name,phone,uid,level) values(?,?,?,?,?)",params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("添加失败", e);
        }
    }

    @Override
    public void updateDefault(int id, Integer uid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("update tb_address set level=0 where uid=?", uid);
            qr.update("update tb_address set level=1 where id=? and uid=?", id,uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("默认地址设置失败", e);
        }
    }

    @Override
    public void delete(int id) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_address where id=?", id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("删除地址失败", e);
        }
    }

    @Override
    public void update(Address address) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        Object[] params={address.getName(),address.getPhone(),address.getDetail(),address.getId()};
        try {
            qr.update("update tb_address set name=?,phone=?,detail=? where id=?", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改地址失败", e);
        }
    }

    @Override
    public Address findByAid(Integer aid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_address where id=?", new BeanHandler<Address>(Address.class),aid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询地址失败", e);
        }
    }
}
