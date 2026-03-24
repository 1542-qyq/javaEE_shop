package com.ch.ch9.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("goodstypetable")
public class GoodsType {
    @TableId
    private Integer id;
    private String typename;

    public GoodsType() {
    }

    public GoodsType(Integer id, String typename) {
        this.id = id;
        this.typename = typename;
    }

    // Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}