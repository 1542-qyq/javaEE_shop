package com.ch.ch9.repository.admin;

import com.ch.ch9.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminTypeRepository")
@Mapper
public interface TypeRepository {
    List<GoodsType> findAll();
    List<GoodsType> findByTname(String tname);
    GoodsType findById(Integer id);
    int add(GoodsType type);
    int update(GoodsType type);
    int delete(Integer id);
}
