package com.ch.ch9.service.before;

import com.ch.ch9.entity.Cart;
import com.ch.ch9.entity.Order;
import com.ch.ch9.repository.before.CartRepository;
import com.ch.ch9.repository.before.OrderRepository;
import com.ch.ch9.service.before.CartService;
import com.ch.ch9.service.before.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public String createOrderConfirm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/toLogin";
        }

        // 获取购物车中的商品
        List<Cart> cartItems = cartService.getUserCartItems(session);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("errorMessage", "购物车为空，无法创建订单");
            return "user/cart";
        }

        // 计算总金额
        BigDecimal totalAmount = calculateCartTotal(session);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("itemCount", cartItems.size());

        return "user/orderConfirm";
    }

    @Override
    @Transactional
    public String submitOrder(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/toLogin";
        }

        // 获取购物车商品
        List<Cart> cartItems = cartService.getUserCartItems(session);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("errorMessage", "购物车为空，无法提交订单");
            return "user/cart";
        }

        try {
            // 计算总金额
            BigDecimal totalAmount = calculateCartTotal(session);

            // 创建订单
            Order order = new Order(userId, totalAmount);
            order.setOrderDate(new Date());
            order.setStatus(Order.STATUS_PENDING);

            int result = orderRepository.createOrder(order);

            if (result > 0) {
                // 清空购物车
                for (Cart cartItem : cartItems) {
                    cartRepository.deleteFromCart(cartItem.getId());
                }

                model.addAttribute("orderId", order.getId());
                model.addAttribute("orderAmount", totalAmount);
                model.addAttribute("successMessage", "订单提交成功！");

                return "user/orderSuccess";
            } else {
                model.addAttribute("errorMessage", "订单创建失败，请重试");
                return "user/orderConfirm";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "系统错误，请稍后重试");
            return "user/orderConfirm";
        }
    }

    @Override
    public String getMyOrders(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/toLogin";
        }

        List<Order> orders = orderRepository.findByUserId(userId);

        model.addAttribute("orders", orders);
        model.addAttribute("orderCount", orders.size());

        return "user/myOrders";
    }

    @Override
    public String getOrderDetail(Integer orderId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/toLogin";
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            model.addAttribute("errorMessage", "订单不存在");
            return "user/myOrders";
        }

        // 检查订单是否属于当前用户
        if (!order.getBusertableId().equals(userId)) {
            model.addAttribute("errorMessage", "无权查看此订单");
            return "user/myOrders";
        }

        model.addAttribute("order", order);

        return "user/orderDetail";
    }

    @Override
    public BigDecimal calculateCartTotal(HttpSession session) {
        List<Cart> cartItems = cartService.getUserCartItems(session);
        BigDecimal total = BigDecimal.ZERO;

        for (Cart cartItem : cartItems) {
            if (cartItem.getGoods() != null && cartItem.getGoods().getGprice() != null) {
                BigDecimal price = cartItem.getGoods().getGprice();
                BigDecimal quantity = new BigDecimal(cartItem.getShoppingnum());
                total = total.add(price.multiply(quantity));
            }
        }

        return total;
    }
}