package com.qf.service.impl;

import com.qf.dao.AddressDao;
import com.qf.dao.impl.addressDaoImpl;
import com.qf.domain.Address;
import com.qf.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    AddressDao addressDao=new addressDaoImpl();
    @Override
    public List<Address> findByUid(int uid) {
        return addressDao.findByUid(uid);
    }

    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public void updateDefault(int id, Integer uid) {
        addressDao.updateDefault(id,uid);
    }

    @Override
    public void delete(int id) {
        addressDao.delete(id);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }
}
