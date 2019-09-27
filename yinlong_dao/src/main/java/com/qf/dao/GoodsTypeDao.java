package com.qf.dao;

import com.qf.domain.GoodsType;

import java.util.List;

public interface GoodsTypeDao {
    List<GoodsType> findTypeByLevel(int level);

    GoodsType findById(Integer typeid);
}
