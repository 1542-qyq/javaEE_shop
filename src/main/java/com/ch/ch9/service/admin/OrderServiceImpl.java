package com.ch.ch9.service.admin;

import com.ch.ch9.entity.Order;
import com.ch.ch9.mapper.admin.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.math.BigDecimal;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        logger.info("OrderServiceImpl: 开始查询所有订单");
        try {
            List<Order> orders = orderMapper.selectList(null);
            logger.info("OrderServiceImpl: 查询所有订单成功，共 {} 条记录", orders != null ? orders.size() : 0);

            if (logger.isDebugEnabled() && orders != null) {
                for (int i = 0; i < Math.min(orders.size(), 5); i++) {
                    logger.debug("OrderServiceImpl: 订单{}: {}", i + 1, orders.get(i));
                }
                if (orders.size() > 5) {
                    logger.debug("OrderServiceImpl: 还有 {} 条订单未显示...", orders.size() - 5);
                }
            }

            return orders;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 查询所有订单失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询订单列表失败", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByBusertableId(Integer busertableId) {
        logger.info("OrderServiceImpl: 开始根据用户ID查询订单，用户ID: {}", busertableId);
        try {
            if (busertableId == null || busertableId <= 0) {
                logger.warn("OrderServiceImpl: 用户ID参数无效: {}", busertableId);
                throw new IllegalArgumentException("用户ID参数无效");
            }

            List<Order> orders = orderMapper.findByBusertableId(busertableId);
            logger.info("OrderServiceImpl: 根据用户ID查询订单成功，用户ID: {}, 共 {} 条记录",
                    busertableId, orders != null ? orders.size() : 0);

            if (logger.isDebugEnabled() && orders != null) {
                for (Order order : orders) {
                    logger.debug("OrderServiceImpl: 用户订单详情: {}", order);
                }
            }

            return orders;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 根据用户ID查询订单失败，用户ID: {}: {}",
                    busertableId, e.getMessage(), e);
            throw new RuntimeException("查询用户订单失败", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer id) {
        logger.info("OrderServiceImpl: 开始根据ID查询订单，订单ID: {}", id);
        try {
            if (id == null || id <= 0) {
                logger.warn("OrderServiceImpl: 订单ID参数无效: {}", id);
                throw new IllegalArgumentException("订单ID参数无效");
            }

            Order order = orderMapper.selectById(id);

            if (order == null) {
                logger.warn("OrderServiceImpl: 未找到ID为 {} 的订单", id);
            } else {
                logger.info("OrderServiceImpl: 根据ID查询订单成功，订单ID: {}, 订单详情: {}", id, order);
            }

            return order;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 根据ID查询订单失败，订单ID: {}: {}",
                    id, e.getMessage(), e);
            throw new RuntimeException("查询订单详情失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Order order) throws Exception {
        logger.info("OrderServiceImpl: 开始添加订单，订单信息: {}", order);
        try {
            // 参数验证
            if (order == null) {
                throw new IllegalArgumentException("订单信息不能为空");
            }
            if (order.getBusertableId() == null || order.getBusertableId() <= 0) {
                throw new IllegalArgumentException("用户ID不能为空且必须大于0");
            }
            if (order.getOrderDate() == null) {
                throw new IllegalArgumentException("订单日期不能为空");
            }
            if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("订单金额不能为空且必须大于0");
            }
            if (order.getStatus() == null) {
                throw new IllegalArgumentException("订单状态不能为空");
            }

            // 记录插入前的状态（如果有ID）
            Integer beforeId = order.getId();

            // 执行插入
            int affectedRows = orderMapper.insert(order);

            logger.info("OrderServiceImpl: 添加订单成功，影响行数: {}", affectedRows);
            logger.info("OrderServiceImpl: 插入前订单ID: {}, 插入后订单ID: {}",
                    beforeId, order.getId());

            // 验证插入是否成功
            if (order.getId() == null || order.getId() <= 0) {
                logger.error("OrderServiceImpl: 添加订单后未获取到有效的订单ID");
                throw new RuntimeException("添加订单失败，未生成订单ID");
            }

            logger.info("OrderServiceImpl: 订单添加完成，新订单ID: {}", order.getId());

        } catch (IllegalArgumentException e) {
            logger.warn("OrderServiceImpl: 添加订单参数验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 添加订单失败: {}", e.getMessage(), e);

            // 根据异常类型提供更详细的错误信息
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                throw new RuntimeException("添加订单失败:用户ID不存在，请检查用户ID是否正确", e);
            }
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("添加订单失败:订单已存在", e);
            }
            if (e.getMessage() != null && e.getMessage().contains("Data truncation")) {
                throw new RuntimeException("添加订单失败:数据长度超出限制", e);
            }

            throw new RuntimeException("添加订单失败:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Order order) throws Exception {
        logger.info("OrderServiceImpl: 开始更新订单，订单信息: {}", order);
        try {
            // 参数验证
            if (order == null) {
                throw new IllegalArgumentException("订单信息不能为空");
            }
            if (order.getId() == null || order.getId() <= 0) {
                throw new IllegalArgumentException("订单ID不能为空且必须大于0");
            }

            // 检查订单是否存在
            Order existingOrder = orderMapper.selectById(order.getId());
            if (existingOrder == null) {
                throw new RuntimeException("订单不存在，ID: " + order.getId());
            }

            logger.info("OrderServiceImpl: 更新前订单信息: {}", existingOrder);

            // 执行更新
            int affectedRows = orderMapper.updateById(order);

            logger.info("OrderServiceImpl: 更新订单成功，影响行数: {}, 订单ID: {}",
                    affectedRows, order.getId());

            if (affectedRows == 0) {
                logger.warn("OrderServiceImpl: 更新订单未影响任何行，订单ID: {}", order.getId());
                throw new RuntimeException("更新订单失败，未找到对应的订单记录");
            }

            // 验证更新结果
            Order updatedOrder = orderMapper.selectById(order.getId());
            logger.info("OrderServiceImpl: 更新后订单信息: {}", updatedOrder);

        } catch (IllegalArgumentException e) {
            logger.warn("OrderServiceImpl: 更新订单参数验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 更新订单失败: {}", e.getMessage(), e);

            // 根据异常类型提供更详细的错误信息
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                throw new RuntimeException("更新订单失败:用户ID不存在，请检查用户ID是否正确", e);
            }

            throw new RuntimeException("更新订单失败:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) throws Exception {
        logger.info("OrderServiceImpl: 开始删除订单，订单ID: {}", id);
        try {
            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("订单ID不能为空且必须大于0");
            }

            // 检查订单是否存在
            Order existingOrder = orderMapper.selectById(id);
            if (existingOrder == null) {
                throw new RuntimeException("订单不存在，无法删除，ID: " + id);
            }

            logger.info("OrderServiceImpl: 删除前订单信息: {}", existingOrder);

            // 执行删除
            int affectedRows = orderMapper.deleteById(id);

            logger.info("OrderServiceImpl: 删除订单成功，影响行数: {}, 订单ID: {}",
                    affectedRows, id);

            if (affectedRows == 0) {
                logger.warn("OrderServiceImpl: 删除订单未影响任何行，订单ID: {}", id);
                throw new RuntimeException("删除订单失败，未找到对应的订单记录");
            }

            // 重置自增ID，实现删除后重新排序
            orderMapper.resetAutoIncrement();

        } catch (IllegalArgumentException e) {
            logger.warn("OrderServiceImpl: 删除订单参数验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 删除订单失败: {}", e.getMessage(), e);

            // 根据异常类型提供更详细的错误信息
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                throw new RuntimeException("删除订单失败:存在关联数据，请先删除订单详情", e);
            }

            throw new RuntimeException("删除订单失败:" + e.getMessage(), e);
        }
    }

    /**
     * 辅助方法:检查服务是否正常工作
     */
    public String checkServiceStatus() {
        try {
            int count = orderMapper.selectList(null).size();
            return String.format("OrderService状态正常，当前共有 %d 个订单", count);
        } catch (Exception e) {
            return String.format("OrderService状态异常: %s", e.getMessage());
        }
    }

    /**
     * 辅助方法:统计订单数量
     */
    @Transactional(readOnly = true)
    public int countAllOrders() {
        try {
            List<Order> orders = orderMapper.selectList(null);
            return orders != null ? orders.size() : 0;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 统计订单数量失败: {}", e.getMessage(), e);
            return -1;
        }
    }

    /**
     * 辅助方法:根据状态统计订单数量
     */
    @Transactional(readOnly = true)
    public int countByStatus(String status) {
        logger.info("OrderServiceImpl: 根据状态统计订单数量，状态: {}", status);
        try {
            if (status == null || status.trim().isEmpty()) {
                throw new IllegalArgumentException("状态不能为空或空字符串");
            }

            List<Order> allOrders = orderMapper.selectList(null);
            if (allOrders == null || allOrders.isEmpty()) {
                return 0;
            }

            int count = 0;
            for (Order order : allOrders) {
                if (order.getStatus() != null && order.getStatus().equals(status)) {
                    count++;
                }
            }

            logger.info("OrderServiceImpl: 状态为 {} 的订单数量: {}", status, count);
            return count;
        } catch (Exception e) {
            logger.error("OrderServiceImpl: 根据状态统计订单数量失败: {}", e.getMessage(), e);
            return -1;
        }
    }
}