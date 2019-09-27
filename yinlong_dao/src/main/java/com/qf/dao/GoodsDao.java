package com.qf.dao;

import com.qf.domain.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDao {

    List<Goods> findPageByWhere(@Param("condition") String condition);

    long getCount(@Param("condition") String condition);

    Goods findById(int gid);
}
