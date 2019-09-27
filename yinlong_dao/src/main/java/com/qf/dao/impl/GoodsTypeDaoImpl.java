package com.qf.dao.impl;

import com.qf.dao.GoodsTypeDao;
import com.qf.domain.GoodsType;
import com.qf.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class GoodsTypeDaoImpl implements GoodsTypeDao {
    @Override
    public List<GoodsType> findTypeByLevel(int level) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_goods_type where level=?",new BeanListHandler<>(GoodsType.class),level);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("查询商品类型失败", e);
        }
    }

    @Override
    public GoodsType findById(Integer typeid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_goods_type where id=?", new BeanHandler<>(GoodsType.class),typeid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("根据id查询商品类型失败", e);
        }
    }
}
