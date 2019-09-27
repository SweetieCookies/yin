package com.qf.dao.impl;

import com.qf.dao.GoodsDao;
import com.qf.domain.Goods;
import com.qf.utils.DataSourceUtils;
import com.qf.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {

    @Override
    public List<Goods> findPageByWhere( String condition) {
        String sql="select * from tb_goods";
        if(!StringUtils.isEmpty(condition)){
            sql=sql+" where "+condition;
        }
        sql=sql + " order by id limit ?,?";
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
          return   qr.query(sql, new BeanListHandler<>(Goods.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("查询商品失败", e);
        }
    }

    @Override
    public long getCount(String condition) {
        String sql="select count(*) from tb_goods";
        if(!StringUtils.isEmpty(condition)){
            sql=sql+" where "+condition;
        }
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return (long) qr.query(sql, new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Goods findById(int gid) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_goods where id=?", new BeanHandler<>(Goods.class),gid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("根据id查询的商品失败", e);
        }
    }
}
