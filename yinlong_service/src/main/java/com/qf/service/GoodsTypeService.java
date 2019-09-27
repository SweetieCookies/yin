package com.qf.service;

import com.qf.domain.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    List<GoodsType> getGoodsTypeByLevel(int level);

}
