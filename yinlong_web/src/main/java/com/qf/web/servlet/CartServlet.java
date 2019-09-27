package com.qf.web.servlet;

import com.qf.domain.Cart;
import com.qf.domain.Goods;
import com.qf.domain.User;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.GoodsServiceImpl;
import com.qf.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "CartServlet", value = "/cartservlet")
public class CartServlet extends BaseServlet {
    public String addCart(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        if(StringUtils.isEmpty(goodsId)){
            return "/index.jsp";
        }
        CartService cartService=new CartServiceImpl();
        Cart cart = cartService.findByUidAndGid(user.getId(), Integer.parseInt(goodsId));
        GoodsService goodsService=new GoodsServiceImpl();
        Goods goods = goodsService.findById(Integer.parseInt(goodsId));
        if(cart==null){
            Cart cart1=new Cart(user.getId(), Integer.parseInt(goodsId), Integer.parseInt(number), goods.getPrice().multiply(new BigDecimal(Integer.parseInt(number))));
            cartService.addcarts(cart1);
        }else {
            cart.setNum(cart.getNum()+Integer.parseInt(number));
            cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
            cartService.update(cart);
        }
        return "redirect:/cartSuccess.jsp";
    }
    public String getCart(HttpServletRequest request, HttpServletResponse response){
       User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        CartService cartService=new CartServiceImpl();
       List<Cart> carts=cartService.findByUid(user.getId());
       request.getSession().setAttribute("carts",  carts);
        return "/cart.jsp";
    }
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        CartService cartService=new CartServiceImpl();
        Cart cart = cartService.findByUidAndGid(user.getId(), Integer.parseInt(goodsId));
        if(cart!=null){
            if(number.equals("0")){
                cartService.delete(user.getId(),Integer.parseInt(goodsId));
            }else{
                int num = Integer.parseInt(number);
                BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));
                cart.setNum(cart.getNum()+num);
                cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
        }
        return null;
    }
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        CartService cartService=new CartServiceImpl();
        cartService.deleteByUid(user.getId());
        return null;
    }
}
