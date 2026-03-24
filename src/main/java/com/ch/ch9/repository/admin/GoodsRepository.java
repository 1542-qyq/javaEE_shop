package com.ch.ch9.repository.admin;

import com.ch.ch9.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminGoodsRepository")
@Mapper
public interface GoodsRepository {
    List<Goods> findAll();
    List<Goods> findByGname(String gname);
    Goods findById(Integer id);
    int add(Goods goods);
    int update(Goods goods);
    int delete(Integer id);
    void resetAutoIncrement();
}