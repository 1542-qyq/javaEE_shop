package com.ch.ch9.service.before;

import com.ch.ch9.entity.Cart;
import com.ch.ch9.repository.before.CartRepository;
import com.ch.ch9.service.before.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional
    public String addToCart(Integer goodsId, Integer quantity, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "error:请先登录";
        }

        if (goodsId == null || goodsId < 1) {
            return "error:商品ID无效";
        }

        if (quantity == null || quantity < 1) {
            quantity = 1;
        }

        Cart existingCart = cartRepository.findByUserIdAndGoodsId(userId, goodsId);

        try {
            if (existingCart != null) {
                int newQuantity = existingCart.getShoppingnum() + quantity;
                cartRepository.updateQuantity(existingCart.getId(), newQuantity);
                return "success:商品数量已更新";
            } else {
                Cart cart = new Cart(userId, goodsId, quantity);
                cartRepository.addToCart(cart);
                return "success:已成功加入购物车";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error:加入购物车失败，请稍后重试";
        }
    }

    @Override
    @Transactional
    public String deleteFromCart(Integer cartId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "error:请先登录";
        }

        if (cartId == null || cartId < 1) {
            return "error:购物车ID无效";
        }

        try {
            int result = cartRepository.deleteFromCart(cartId);
            if (result > 0) {
                return "success:已成功删除";
            } else {
                return "error:删除失败，商品可能不存在";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error:删除失败，请稍后重试";
        }
    }

    @Override
    @Transactional
    public String updateQuantity(Integer cartId, Integer quantity, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "error:请先登录";
        }

        if (cartId == null || cartId < 1) {
            return "error:购物车ID无效";
        }

        if (quantity == null || quantity < 1) {
            return "error:数量必须大于0";
        }

        try {
            int result = cartRepository.updateQuantity(cartId, quantity);
            if (result > 0) {
                return "success:数量已更新";
            } else {
                return "error:更新失败，商品可能不存在";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error:更新失败，请稍后重试";
        }
    }

    @Override
    public String getCartPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/toLogin";
        }

        List<Cart> cartItems = getUserCartItems(session);
        Double totalAmount = calculateTotal(session);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("itemCount", cartItems.size());

        return "user/cart";
    }

    @Override
    public List<Cart> getUserCartItems(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return List.of();
        }

        List<Cart> cartItems = cartRepository.findByUserIdWithGoods(userId);

        // 计算每个购物车项的小计
        for (Cart cart : cartItems) {
            // 小计在Cart实体类的getSubtotal()方法中自动计算
        }

        return cartItems;
    }

    @Override
    public Double calculateTotal(HttpSession session) {
        List<Cart> cartItems = getUserCartItems(session);

        BigDecimal total = BigDecimal.ZERO;
        for (Cart cart : cartItems) {
            // 小计是 BigDecimal，累加也用 BigDecimal
            if (cart.getSubtotal() != null) {
                total = total.add(cart.getSubtotal());
            }
        }
        // 转换为 Double 返回
        return total.doubleValue();
    }
}