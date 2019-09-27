package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.domain.Cart;
import com.qf.domain.Goods;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao ;
@Autowired
private GoodsService goodsService;
    @Override
    public Cart findByUidAndGid(Integer id, int parseInt) {
        Cart cart = cartDao.findByUidAndGid(id, parseInt);
        return cart;
    }

    @Override
    public void addcarts(Cart cart) {
        cartDao.add(cart);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public List<Cart> findByUid(int Uid) {
        List<Cart> carts = cartDao.findByUid(Uid);
        if(carts!=null){

            for (Cart cart : carts) {
                Goods goods = goodsService.findById(cart.getPid());
                cart.setGoods(goods);
            }
        }
        return carts;
    }

    @Override
    public void delete(int uid, int pid) {
        cartDao.delete(uid,pid);
    }

    @Override
    public void deleteByUid(int uid) {
        cartDao.deleteByUid(uid);
    }
}
