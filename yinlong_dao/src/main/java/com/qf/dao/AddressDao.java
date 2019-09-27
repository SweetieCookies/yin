package com.qf.dao;

import com.qf.domain.Address;

import java.util.List;

public interface AddressDao {
    List<Address> findByUid(int uid);

    void add(Address address);

    void updateDefault(int id, Integer uid);

    void delete(int id);

    void update(Address address);

    Address findByAid(Integer aid);
}
