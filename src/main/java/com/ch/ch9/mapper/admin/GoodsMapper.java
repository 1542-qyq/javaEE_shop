package com.ch.ch9.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminGoodsmapper")
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> findByGname(String gname);
    void resetAutoIncrement();
}