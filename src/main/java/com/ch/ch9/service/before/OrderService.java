package com.ch.ch9.service.before;

import com.ch.ch9.entity.Order;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    /**
     * 创建订单确认页面
     */
    String createOrderConfirm(HttpSession session, Model model);

    /**
     * 提交订单
     */
    String submitOrder(HttpSession session, Model model);

    /**
     * 获取我的订单列表
     */
    String getMyOrders(HttpSession session, Model model);

    /**
     * 根据ID获取订单详情
     */
    String getOrderDetail(Integer orderId, HttpSession session, Model model);

    /**
     * 计算购物车总金额
     */
    BigDecimal calculateCartTotal(HttpSession session);
}