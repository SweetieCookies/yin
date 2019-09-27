package com.qf.service.impl;

import com.qf.dao.*;
import com.qf.dao.impl.*;
import com.qf.domain.Address;
import com.qf.domain.Goods;
import com.qf.domain.Order;
import com.qf.domain.OrderDetail;
import com.qf.service.OrderService;
import com.qf.utils.DataSourceUtils;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDaoImpl();
    @Override
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {
        try {
            DataSourceUtils.beginTransaction();
            //添加订单
            OrderDao orderDao=new OrderDaoImpl();
            orderDao.add(order);
            //添加订单详情
            OrderDetailDao orderDetailDao=new OrderDetailDaoImpl();
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailDao.add(orderDetail);
            }
            //清空购物车
            CartDao cartDao=new CartDaoImpl();
            cartDao.deleteByUid(order.getUid());
            //提交事务
            DataSourceUtils.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.roolback();
        } finally {
            DataSourceUtils.close();
        }

    }

    @Override
    public List<Order> orderQuery(int uid) {
        List<Order> orders = orderDao.findById(uid);
        AddressDao addressDao=new addressDaoImpl();
        List<Address> addList = addressDao.findByUid(uid);
        if(orders!=null&&orders.size()>0){
            for (Order order : orders) {
                //System.out.println(order.toString());
                for (Address address : addList) {
                    if(order.getAid()==address.getId()){
                        order.setAddress(address);
                    }
                }
            }
        }
        return  orders;
    }

    @Override
    public Order orderDetailQuery(String oid) {
       Order order= orderDao.findByOid(oid);
       AddressDao addressDao=new addressDaoImpl();
        Address address = addressDao.findByAid(order.getAid());
        order.setAddress(address);
        List<OrderDetail> orderDetails=orderDao.findOrderDetail(oid);
        order.setOrderDetails(orderDetails);

        //查询订单详情关联的商品
        GoodsDao goodsDao=new GoodsDaoImpl();
        if(orderDetails!=null&&orderDetails.size()>0){
            for (OrderDetail orderDetail : orderDetails) {
                Goods goods = goodsDao.findById(orderDetail.getPid());
                orderDetail.setGoods(goods);
            }
        }
        return order;
    }
}
