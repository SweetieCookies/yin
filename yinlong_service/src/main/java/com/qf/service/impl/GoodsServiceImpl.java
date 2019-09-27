package com.qf.service.impl;

import com.qf.dao.GoodsDao;
import com.qf.dao.GoodsTypeDao;
import com.qf.domain.Goods;
import com.qf.domain.GoodsType;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
   private GoodsTypeDao goodsTypeDao;
    @Override
    public List<Goods> findPageByWhere( String condition) {
        List<Goods> data = goodsDao.findPageByWhere(condition);
     return data;
    }

    @Override
    public Goods findById(int gid) {
       Goods goods= goodsDao.findById(gid);
        Integer typeid = goods.getTypeid();
       GoodsType goodsType= goodsTypeDao.findById(typeid);
       goods.setGoodsType(goodsType);
        return goods;
    }
}
