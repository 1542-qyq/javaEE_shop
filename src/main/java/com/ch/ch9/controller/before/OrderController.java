package com.ch.ch9.controller.before;


import com.ch.ch9.service.before.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller("beforeOrderController")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单确认页面
     */
    @GetMapping("/confirm")
    public String orderConfirm(HttpSession session, Model model) {
        return orderService.createOrderConfirm(session, model);
    }

    /**
     * 提交订单
     */
    @PostMapping("/submit")
    public String submitOrder(HttpSession session, Model model) {
        return orderService.submitOrder(session, model);
    }

    /**
     * 我的订单列表
     */
    @GetMapping("/myOrders")
    public String myOrders(HttpSession session, Model model) {
        return orderService.getMyOrders(session, model);
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Integer orderId,
                              HttpSession session,
                              Model model) {
        return orderService.getOrderDetail(orderId, session, model);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    @ResponseBody
    public String cancelOrder(@RequestParam("orderId") Integer orderId,
                              HttpSession session) {
        // 这里可以添加取消订单的逻辑
        return "success:订单取消功能待实现";
    }
}