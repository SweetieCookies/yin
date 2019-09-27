package com.qf.service;

import com.qf.domain.Goods;

import java.util.List;

public interface GoodsService {
   List<Goods> findPageByWhere(String condition);

    Goods findById(int parseInt);
}
