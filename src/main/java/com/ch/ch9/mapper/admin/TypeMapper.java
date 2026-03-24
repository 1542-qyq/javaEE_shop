package com.ch.ch9.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminTypemapper")
@Mapper
public interface TypeMapper extends BaseMapper<GoodsType> {
    List<GoodsType> findByTname(String tname);
}
