package com.ch.ch9.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;

@TableName("orderbasetable")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("busertable_id")
    private Integer busertableId;

    private BigDecimal amount;

    @TableField("order_date")  // 数据库列名是 order_date
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date orderDate;  // ✅ 改为驼峰命名：orderDate

    private String status;

    // 关联信息
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String userEmail;

    // 状态常量
    public static final String STATUS_PENDING = "待付款";
    public static final String STATUS_PAID = "已付款";
    public static final String STATUS_SHIPPED = "已发货";
    public static final String STATUS_COMPLETED = "已完成";
    public static final String STATUS_CANCELLED = "已取消";

    // 构造方法
    public Order() {
        this.orderDate = new Date();
        this.status = STATUS_PENDING;
    }

    public Order(Integer busertableId, BigDecimal amount) {
        this();
        this.busertableId = busertableId;
        this.amount = amount;
    }

    // Getter和Setter - 使用标准的JavaBean命名
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusertableId() {
        return busertableId;
    }

    public void setBusertableId(Integer busertableId) {
        this.busertableId = busertableId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // ✅ 标准的getter方法：getOrderDate()
    public Date getOrderDate() {
        return orderDate;
    }

    // ✅ 标准的setter方法：setOrderDate()
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 修改其他getter方法也使用驼峰命名
    public String getUserName() {  // ✅ getUserName() 不是 getuserName()
        return userName;
    }

    public void setUserName(String userName) {  // ✅ setUserName()
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // 格式化日期
    public String getFormattedOrderDate() {  // ✅ getFormattedOrderDate()
        if (orderDate == null) {
            return "";
        }
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderDate);
    }

    // 获取状态颜色
    public String getStatusColor() {
        switch (status) {
            case STATUS_PENDING:
                return "warning";
            case STATUS_PAID:
                return "info";
            case STATUS_SHIPPED:
                return "primary";
            case STATUS_COMPLETED:
                return "success";
            case STATUS_CANCELLED:
                return "danger";
            default:
                return "secondary";
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", busertableId=" + busertableId +
                ", amount=" + amount +
                ", orderDate=" + orderDate +  // ✅ 使用orderDate
                ", status='" + status + '\'' +
                '}';
    }
}