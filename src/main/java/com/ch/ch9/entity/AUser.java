package com.ch.ch9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("ausertable")
public class AUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("aname")
    private String aname;

    @TableField("apwd")
    private String apwd;





    // 构造方法
    public AUser() {
    }

    public AUser(String aname, String apwd) {
        this.aname = aname;
        this.apwd = apwd;
    }

    // Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getApwd() {
        return apwd;
    }

    public void setApwd(String apwd) {
        this.apwd = apwd;
    }





    @Override
    public String toString() {
        return "AUser{" +
                "id=" + id +
                ", aname='" + aname + "'" +
                ", apwd='" + apwd + "'" +
                '}';
    }
}