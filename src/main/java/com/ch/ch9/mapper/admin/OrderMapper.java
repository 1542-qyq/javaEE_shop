package com.ch.ch9.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminOrdermapper")
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> findByBusertableId(Integer busertableId);
    void resetAutoIncrement();
}