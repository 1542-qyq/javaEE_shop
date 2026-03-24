package com.ch.ch9.repository.before;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.ch9.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CartRepository extends BaseMapper<Cart>{

    /**
     * 添加商品到购物车
     */
    int addToCart(Cart cart);

    /**
     * 更新购物车中商品数量
     */
    int updateQuantity(@Param("id") Integer cartId, @Param("quantity") Integer quantity);

    /**
     * 从购物车删除商品
     */
    int deleteFromCart(Integer cartId);

    /**
     * 根据用户ID和商品ID查找购物车项
     */
    Cart findByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * 根据用户ID查询购物车所有商品（带商品详情）
     */
    List<Cart> findByUserIdWithGoods(Integer userId);
}