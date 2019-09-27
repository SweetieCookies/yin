package com.qf.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceUtils {
    private  static DruidDataSource dataSource;
    private static ThreadLocal<Connection> threadLocal;
    static {
        Properties properties=new Properties();
        threadLocal=new ThreadLocal<>();
        try {
            properties.load(DataSourceUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    /**
     * @threadLocal 线程局部变量，绑定连接
     * @return
     */
    public  static  Connection getConnection(){
        Connection connection = threadLocal.get();
        if(connection==null){
            try {
                connection=dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocal.set(connection);
        }
        return connection;
    }
    //开启事务
    public static void beginTransaction(){
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  static  void commit(){
        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void roolback(){
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static  void  close(){
        try {
            getConnection().close();
            threadLocal.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
