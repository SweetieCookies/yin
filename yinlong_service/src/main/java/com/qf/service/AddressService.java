package com.qf.service;

import com.qf.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> findByUid(int uid);

    void add(Address address);

    void updateDefault(int id, Integer uid);

    void delete(int id);

    void update(Address address);
}
