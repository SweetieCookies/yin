package com.qf.web.servlet;

import com.qf.dao.AddressDao;
import com.qf.dao.impl.addressDaoImpl;
import com.qf.domain.*;
import com.qf.service.AddressService;
import com.qf.service.CartService;
import com.qf.service.OrderService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.OrderServiceImpl;
import com.qf.utils.RandomUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orderservlet")
public class OrderServlet extends BaseServlet {
    public  String getOrderView(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
       //查询购物的商品
        CartService cartService=new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());
        request.setAttribute("carts",carts);
        //获取收货地址
        AddressService addressService=new AddressServiceImpl();
        List<Address>  addressList=addressService.findByUid(user.getId());
        request.setAttribute("addList", addressList);
        return "/order.jsp";
    }
    public String addOrder(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String aid = request.getParameter("aid");
        CartService cartService=new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());
        if(carts==null||carts.size()==0){
            request.setAttribute("msg", "购物车为空，请先购买商品哟");
            return "/message.jsp";
        }
        String oid = RandomUtils.createOrderId();
        //System.out.println(oid);
        //创建订单详情
        List<OrderDetail> orderDetails=new ArrayList<>();
        BigDecimal sum=new BigDecimal(0);
        for (Cart cart : carts) {
            OrderDetail orderDetail=new OrderDetail(null,oid , cart.getPid(), cart.getNum(), cart.getMoney());
            orderDetails.add(orderDetail);
            sum=sum.add(cart.getMoney());
        }
        Order order=new Order(oid, user.getId(), sum, "1", new Date(), Integer.parseInt(aid));
       // System.out.println(order.toString());
        OrderService orderService=new OrderServiceImpl();
        try {
            orderService.saveOrder(order,orderDetails);
            request.setAttribute("order", order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "订单提交失败："+e.getMessage());
            return "/message.jsp";
        }
    }
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        OrderService orderService=new OrderServiceImpl();
        List<Order> orders=orderService.orderQuery(user.getId());

        request.setAttribute("orderList", orders);
        return "/orderList.jsp";
    }
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String oid = request.getParameter("oid");
        OrderService orderService=new OrderServiceImpl();
        Order order=orderService.orderDetailQuery(oid);
        //根据order查询地址
        AddressDao addressDao=new addressDaoImpl();
        Address address=addressDao.findByAid(order.getAid());
        request.setAttribute("order", order);
        return "/orderDetail.jsp";
    }
}
