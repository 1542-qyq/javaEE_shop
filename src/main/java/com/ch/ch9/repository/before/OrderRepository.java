package com.ch.ch9.repository.before;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface OrderRepository extends BaseMapper<Order>{

    /**
     * 创建订单
     */
    int createOrder(Order order);

    /**
     * 根据ID查询订单
     */
    Order findById(Integer id);

    /**
     * 根据用户ID查询订单列表
     */
    List<Order> findByUserId(Integer userId);

    /**
     * 更新订单状态
     */
    int updateOrderStatus(@Param("id") Integer id, @Param("status") String status);

    /**
     * 查询用户最近的订单
     */
    List<Order> findRecentOrdersByUserId(@Param("userId") Integer userId, @Param("limit") Integer limit);
}