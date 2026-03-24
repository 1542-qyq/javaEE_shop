package com.ch.ch9.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;

@Entity
@Table(name = "carttable")
@TableName("carttable")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer busertableId;      // 用户ID
    private Integer goodstableId;      // 商品ID
    private Integer shoppingnum;       // 购买数量

    // 关联商品信息（非数据库字段）
    @Transient
    private Goods goods;

    // 计算小计（非数据库字段）
    public BigDecimal getSubtotal() {
        if (goods != null && goods.getGprice() != null && shoppingnum != null) {
            return goods.getGprice().multiply(BigDecimal.valueOf(shoppingnum));
        }
        return BigDecimal.ZERO;
    }

    // 构造方法
    public Cart() {
    }

    public Cart(Integer busertableId, Integer goodstableId, Integer shoppingnum) {
        this.busertableId = busertableId;
        this.goodstableId = goodstableId;
        this.shoppingnum = shoppingnum;
    }

    // Getter和Setter
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

    public Integer getGoodstableId() {
        return goodstableId;
    }

    public void setGoodstableId(Integer goodstableId) {
        this.goodstableId = goodstableId;
    }

    public Integer getShoppingnum() {
        return shoppingnum;
    }

    public void setShoppingnum(Integer shoppingnum) {
        this.shoppingnum = shoppingnum;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", busertableId=" + busertableId +
                ", goodstableId=" + goodstableId +
                ", shoppingnum=" + shoppingnum +
                '}';
    }
}