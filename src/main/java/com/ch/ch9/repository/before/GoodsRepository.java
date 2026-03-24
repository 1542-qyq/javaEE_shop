package com.ch.ch9.repository.before;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface GoodsRepository extends BaseMapper<Goods>{

    // 查询所有商品（带分页）
    List<Goods> findAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    // 根据ID查询商品详情
    Goods findById(Integer id);

    // 根据商品名称模糊查询
    List<Goods> searchByName(@Param("keyword") String keyword);

    // 查询商品总数
    int countAll();
}