package com.ch.ch9.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

@TableName("goodstable")
public class Goods {
    @TableId
    private Integer id;
    private String gname;
    private BigDecimal gprice;
    private Integer gstore;
    private String gpicture;
    @TableField("goodstype_id")
    private Integer goodstypeId;
    @TableField(exist = false)
    private String typename; // 商品类型名称（关联查询）

    // 构造方法
    public Goods() {
    }

    public Goods(Integer id, String gname, BigDecimal gprice) {
        this.id = id;
        this.gname = gname;
        this.gprice = gprice;
    }

    // Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public BigDecimal getGprice() {
        return gprice;
    }

    public void setGprice(BigDecimal gprice) {
        this.gprice = gprice;
    }

    public Integer getGstore() {
        return gstore;
    }

    public void setGstore(Integer gstore) {
        this.gstore = gstore;
    }

    public String getGpicture() {
        return gpicture;
    }

    public void setGpicture(String gpicture) {
        this.gpicture = gpicture;
    }

    public Integer getGoodstypeId() {
        return goodstypeId;
    }

    public void setGoodstypeId(Integer goodstypeId) {
        this.goodstypeId = goodstypeId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getImageUrl() {
        if (gpicture != null && !gpicture.isEmpty()) {
            String picture = gpicture.trim();
            // 移除开头的斜杠和可能的 images/ 前缀
            while (picture.startsWith("/")) {
                picture = picture.substring(1);
            }
            if (picture.startsWith("images/")) {
                picture = picture.substring(7);
            }
            // 确保路径正确
            return "/ch9/images/" + picture;
        }
        return "/ch9/images/default.png";  // 默认图片
    }

    // 检查是否有库存
    public boolean isInStock() {
        return gstore != null && gstore > 0;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", gprice=" + gprice +
                ", gstore=" + gstore +
                ", typename='" + typename + '\'' +
                '}';
    }
}