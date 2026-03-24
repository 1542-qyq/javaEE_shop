package com.ch.ch9.service.admin;

import com.ch.ch9.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> findAll();
    List<Goods> findByGname(String gname);
    Goods findById(Integer id);
    void add(Goods goods) throws Exception;
    void update(Goods goods) throws Exception;
    void delete(Integer id) throws Exception;
}