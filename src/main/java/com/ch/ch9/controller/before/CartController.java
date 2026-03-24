package com.ch.ch9.controller.before;

import com.ch.ch9.service.before.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller("beforeCartController")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 购物车页面
     */
    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        return cartService.getCartPage(session, model);
    }

    /**
     * 添加商品到购物车（AJAX接口）
     */
    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@RequestParam("goodsId") Integer goodsId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                            HttpSession session) {
        return cartService.addToCart(goodsId, quantity, session);
    }

    /**
     * 从购物车删除商品（AJAX接口）
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteFromCart(@RequestParam("cartId") Integer cartId,
                                 HttpSession session) {
        return cartService.deleteFromCart(cartId, session);
    }

    /**
     * 更新购物车商品数量（AJAX接口）
     */
    @PostMapping("/update")
    @ResponseBody
    public String updateQuantity(@RequestParam("cartId") Integer cartId,
                                 @RequestParam("quantity") Integer quantity,
                                 HttpSession session) {
        return cartService.updateQuantity(cartId, quantity, session);
    }

    /**
     * 获取购物车中商品数量（AJAX接口）
     */
    @GetMapping("/count")
    @ResponseBody
    public String getCartItemCount(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "0";
        }

        // 这里可以添加获取购物车数量的逻辑
        // 暂时返回一个占位符
        return "0";
    }
}