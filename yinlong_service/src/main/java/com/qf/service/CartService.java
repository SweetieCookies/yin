package com.qf.service;

import com.qf.domain.Cart;

import java.util.List;

public interface CartService {
    Cart findByUidAndGid(Integer id, int parseInt);

    void addcarts(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int Uid);

    void delete(int uid, int pid);

    void deleteByUid(int uid);
}
