package com.ch.ch9.controller.admin;

import com.ch.ch9.entity.Order;
import com.ch.ch9.service.admin.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends AdminBaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "busertable_id", required = false) Integer busertable_id,
                       Model model, HttpSession session) {
        logger.info("进入订单列表页面，查询条件: busertable_id = {}", busertable_id);
        try {
            List<Order> orderList;
            if (busertable_id != null && busertable_id > 0) {
                orderList = orderService.findByBusertableId(busertable_id);
                logger.info("根据用户ID查询订单完成，共 {} 条记录", orderList.size());
                logger.info("查询到的订单列表: {}", orderList);
            } else {
                orderList = orderService.findAll();
                logger.info("订单列表加载完成，共 {} 条记录", orderList.size());
                logger.info("所有订单列表: {}", orderList);
            }
            model.addAttribute("orderList", orderList);
            model.addAttribute("busertable_id", busertable_id);

            // 添加测试数据
            logger.info("Model中的orderList: {}", model.getAttribute("orderList"));

        } catch (Exception e) {
            logger.error("加载订单列表失败: {}", e.getMessage(), e);
            model.addAttribute("error", "加载订单列表失败: " + e.getMessage());
        }
        return "admin/orderList";
    }

    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(@ModelAttribute("order") Order order) {
        logger.info("进入添加订单页面");
        return "admin/orderAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("order") Order order) throws Exception {
        logger.info("执行添加订单操作: {}", order);
        try {
            orderService.add(order);
            logger.info("订单添加成功: {}", order);
        } catch (Exception e) {
            logger.error("订单添加失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/order/list";
    }

    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(@RequestParam Integer id, Model model) {
        logger.info("进入编辑订单页面，ID: {}", id);
        try {
            Order order = orderService.findById(id);
            model.addAttribute("order", order);
            logger.info("加载待编辑订单信息: {}", order);
        } catch (Exception e) {
            logger.error("加载待编辑订单信息失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "admin/orderEdit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute("order") Order order) throws Exception {
        logger.info("执行编辑订单操作: {}", order);
        try {
            orderService.update(order);
            logger.info("订单编辑成功: {}", order);
        } catch (Exception e) {
            logger.error("订单编辑失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/order/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam Integer id) throws Exception {
        logger.info("执行删除订单操作，ID: {}", id);
        try {
            orderService.delete(id);
            logger.info("订单删除成功，ID: {}", id);
        } catch (Exception e) {
            logger.error("订单删除失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/order/list";
    }
}