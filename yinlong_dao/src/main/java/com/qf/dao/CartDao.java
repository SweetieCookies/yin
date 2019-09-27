package com.qf.dao;

import com.qf.domain.Cart;

import java.util.List;

public interface CartDao {
    Cart findByUidAndGid(Integer id, int parseInt);

    void add(Cart cart);


    void update(Cart cart);

    List<Cart> findByUid(int uid);

    void delete(int uid, int pid);

    void deleteByUid(int uid);

}
