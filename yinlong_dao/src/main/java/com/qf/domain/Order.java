package com.qf.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  `id` varchar(100) NOT NULL,
 *   `uid` int(11) DEFAULT NULL,
 *   `money` decimal(12,8) DEFAULT NULL,
 *   `status` varchar(10) DEFAULT NULL,
 *   `time` datetime DEFAULT NULL,
 *   `aid` int(11) DEFAULT NULL,
 */
public class Order {
    private String id;
    private Integer uid;
    private BigDecimal money;
    private String status;
    private Date time;
    private Integer aid;
    private  Address address;
    private List<OrderDetail> orderDetails;
    public Order() {
    }

    public Order(String id, Integer uid, BigDecimal money, String status, Date time, Integer aid) {
        this.id = id;
        this.uid = uid;
        this.money = money;
        this.status = status;
        this.time = time;
        this.aid = aid;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", uid=" + uid +
                ", money=" + money +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", aid=" + aid +
                ", address=" + address +
                '}';
    }
}
