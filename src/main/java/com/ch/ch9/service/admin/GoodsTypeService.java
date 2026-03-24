package com.ch.ch9.service.admin;

import com.ch.ch9.entity.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    List<GoodsType> findAll();
    List<GoodsType> findByTname(String tname);
    GoodsType findById(Integer id);
    void add(GoodsType goodsType) throws Exception;
    void update(GoodsType goodsType) throws Exception;
    void delete(Integer id) throws Exception;
}