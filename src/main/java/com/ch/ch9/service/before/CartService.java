package com.ch.ch9.service.before;

import com.ch.ch9.entity.Cart;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CartService {

    /**
     * 添加商品到购物车
     */
    String addToCart(Integer goodsId, Integer quantity, HttpSession session);

    /**
     * 从购物车删除商品
     */
    String deleteFromCart(Integer cartId, HttpSession session);

    /**
     * 更新购物车中商品数量
     */
    String updateQuantity(Integer cartId, Integer quantity, HttpSession session);

    /**
     * 获取购物车页面
     */
    String getCartPage(HttpSession session, Model model);

    /**
     * 获取购物车中所有商品
     */
    List<Cart> getUserCartItems(HttpSession session);

    /**
     * 计算购物车总金额
     */
    Double calculateTotal(HttpSession session);
}