package com.qf.service.impl;

import com.qf.dao.GoodsTypeDao;
import com.qf.domain.GoodsType;
import com.qf.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
//@Transactional(propagation = Propagation.REQUIRED,readOnly = false,rollbackFor = Exception.class)
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Override
    public List<GoodsType> getGoodsTypeByLevel(int level) {
        List<GoodsType> goodsTypeList = goodsTypeDao.findTypeByLevel(level);
        return goodsTypeList;
    }
}
